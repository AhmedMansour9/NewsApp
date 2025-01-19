package com.eaapps.search.data.datasource.remote

import com.eaapps.core.BuildConfig
import com.eaapps.core.base.data.network.safeCall
import com.eaapps.search.data.datasource.remote.model.ArticleDto
import com.eaapps.search.data.datasource.remote.service.SearchApiService
import javax.inject.Inject

interface SearchRemoteDatasource {
    suspend fun searchArticle(query: String, category: String): List<ArticleDto>

}

class SearchRemoteDatasourceImpl @Inject constructor(private val apiService: SearchApiService) : SearchRemoteDatasource {
    override suspend fun searchArticle(query: String, category: String): List<ArticleDto> = safeCall(onSuccess = {
        apiService.searchArticle(apiKey = BuildConfig.API_KEY, query = query, category = category)
    }, onError = { throw it }).articles


}