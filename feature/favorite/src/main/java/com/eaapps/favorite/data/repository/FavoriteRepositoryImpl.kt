package com.eaapps.favorite.data.repository

import com.eaapps.favorite.data.datasource.local.FavoriteLocalDatasource
import com.eaapps.favorite.data.datasource.local.toDomainEntity
import com.eaapps.favorite.data.datasource.local.toLocalEntity
import com.eaapps.favorite.domain.entity.FavoriteEntity
import com.eaapps.favorite.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(private val local: FavoriteLocalDatasource) : FavoriteRepository {
    override suspend fun addArticleToFavorite(entity: FavoriteEntity) = local.addArticleToFavorite(entity.toLocalEntity())

    override suspend fun removeArticleFromFavorite(url: String) = local.removeArticleFromFavorite(url)

    override fun getArticlesInFavorite(): Flow<List<FavoriteEntity>> = local.getArticlesInFavorite().map {
        it.map { it.toDomainEntity() }
    }
}