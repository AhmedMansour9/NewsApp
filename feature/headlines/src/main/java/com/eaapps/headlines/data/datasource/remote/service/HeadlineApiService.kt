package com.eaapps.headlines.data.datasource.remote.service

import com.eaapps.headlines.data.datasource.remote.model.HeadlineResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HeadlineApiService {

    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("apiKey") apiKey: String,
    ): Response<HeadlineResponseDto>

}