package com.eaapps.headlines.data.datasource.remote

import com.eaapps.core.BuildConfig
import com.eaapps.core.base.data.network.safeCall
import com.eaapps.headlines.data.datasource.remote.model.HeadlineResponseDto
import com.eaapps.headlines.data.datasource.remote.service.HeadlineApiService
import javax.inject.Inject

interface HeadlineRemoteDatasource {

    suspend fun getTopHeadline(country: String, category: String): HeadlineResponseDto

}

class HeadlineRemoteDatasourceImpl @Inject constructor(private val apiService: HeadlineApiService) : HeadlineRemoteDatasource {
    override suspend fun getTopHeadline(country: String, category: String): HeadlineResponseDto = safeCall(onSuccess = {
        apiService.getTopHeadlines(apiKey = BuildConfig.API_KEY, country = country, category = category)
    }, onError = { throw it })


}