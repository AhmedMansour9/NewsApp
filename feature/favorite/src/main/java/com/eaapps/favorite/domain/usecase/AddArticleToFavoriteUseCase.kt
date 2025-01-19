package com.eaapps.favorite.domain.usecase

import com.eaapps.favorite.domain.entity.FavoriteEntity
import com.eaapps.favorite.domain.repository.FavoriteRepository
import javax.inject.Inject

class AddArticleToFavoriteUseCase @Inject constructor(private val repository: FavoriteRepository) {
    suspend operator fun invoke(entity: FavoriteEntity) = repository.addArticleToFavorite(entity)
}