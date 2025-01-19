package com.eaapps.search.data.repository

import com.eaapps.search.data.datasource.remote.SearchRemoteDatasource
import com.eaapps.search.data.datasource.remote.model.toDomainEntity
import com.eaapps.search.domain.entity.SearchEntity
import com.eaapps.search.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(private val remote: SearchRemoteDatasource) : SearchRepository {
    override suspend fun searchArticle(query: String, category: String): List<SearchEntity> =
        remote.searchArticle(query, category).sortedByDescending { it.publishedAt }.map { it.toDomainEntity() }
}