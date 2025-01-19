package com.eaapps.headlines.data.repository

import com.eaapps.core.base.data.network.networkBoundResource
import com.eaapps.core.base.domain.FlowResultSource
import com.eaapps.headlines.data.datasource.local.HeadlineLocalDatasource
import com.eaapps.headlines.data.datasource.local.toDomainEntity
import com.eaapps.headlines.data.datasource.remote.HeadlineRemoteDatasource
import com.eaapps.headlines.data.datasource.remote.model.toLocalEntity
import com.eaapps.headlines.domain.entity.HeadlineEntity
import com.eaapps.headlines.domain.repository.HeadlineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HeadlineRepositoryImpl @Inject constructor(private val remote: HeadlineRemoteDatasource, private val local: HeadlineLocalDatasource) :
    HeadlineRepository {

    override fun getHeadlines(country: String, category: String): Flow<FlowResultSource<List<HeadlineEntity>>> = networkBoundResource(query = {
        local.getTopHeadline().map { it -> it.sortedByDescending { it.date }.map { it.toDomainEntity() } }
    }, fetch = {
        remote.getTopHeadline(country, category)
    }, saveFetchResult = { it ->
        local.clearTopHeadline()
        local.insertTopHeadline(it.articles.map { it.toLocalEntity() })
    })


}