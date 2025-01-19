package com.eaapps.favorite.data.datasource.local

import com.eaapps.database.dto.FavoriteDao
import com.eaapps.database.entities.FavoriteLocalEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface FavoriteLocalDatasource {

    suspend fun addArticleToFavorite(entity: FavoriteLocalEntity)

    suspend fun removeArticleFromFavorite(url: String)

    fun getArticlesInFavorite(): Flow<List<FavoriteLocalEntity>>

}

class FavoriteLocalDatasourceImpl @Inject constructor(private val favoriteDao: FavoriteDao) : FavoriteLocalDatasource {
    override suspend fun addArticleToFavorite(entity: FavoriteLocalEntity) = withContext(Dispatchers.IO) {
        favoriteDao.addFavoriteStatus(entity)
    }

    override suspend fun removeArticleFromFavorite(url: String) = withContext(Dispatchers.IO) {
        favoriteDao.removeArticleFromFavorite(url)
    }

    override fun getArticlesInFavorite(): Flow<List<FavoriteLocalEntity>> = favoriteDao.getFavoriteArticles()

}