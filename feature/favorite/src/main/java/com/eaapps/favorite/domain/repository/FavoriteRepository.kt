package com.eaapps.favorite.domain.repository

import com.eaapps.favorite.domain.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    suspend fun addArticleToFavorite(entity:FavoriteEntity)

    suspend fun removeArticleFromFavorite(url:String)

    fun getArticlesInFavorite():Flow<List<FavoriteEntity>>
}