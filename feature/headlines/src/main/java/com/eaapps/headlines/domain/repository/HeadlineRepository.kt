package com.eaapps.headlines.domain.repository

import com.eaapps.core.base.domain.FlowResultSource
import com.eaapps.headlines.domain.entity.HeadlineEntity
import kotlinx.coroutines.flow.Flow

interface HeadlineRepository {

    fun getHeadlines(country:String,category:String): Flow<FlowResultSource<List<HeadlineEntity>>>

}