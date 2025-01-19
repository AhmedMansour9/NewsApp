package com.eaapps.search.domain.repository

import com.eaapps.search.domain.entity.SearchEntity

interface SearchRepository {

    suspend fun searchArticle(query:String,category:String): List<SearchEntity>

}