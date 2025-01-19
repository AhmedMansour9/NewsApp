package com.eaapps.search.data.datasource.remote.service

import com.eaapps.search.data.datasource.remote.model.SearchResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApiService {

    @GET("v2/top-headlines")
    suspend fun searchArticle(
        @Query("q") query: String,
        @Query("category") category: String,
        @Query("apiKey") apiKey: String,
    ): Response<SearchResponseDto>

}