package com.eaapps.favorite.domain.usecase

import com.eaapps.favorite.domain.repository.FavoriteRepository
import javax.inject.Inject

class RemoveArticleToFavoriteUseCase @Inject constructor(private val repository: FavoriteRepository) {
    suspend operator fun invoke(url: String) = repository.removeArticleFromFavorite(url)
}