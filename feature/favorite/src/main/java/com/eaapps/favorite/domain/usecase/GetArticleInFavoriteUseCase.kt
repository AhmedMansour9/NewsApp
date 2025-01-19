package com.eaapps.favorite.domain.usecase

import com.eaapps.favorite.domain.repository.FavoriteRepository
import javax.inject.Inject

class GetArticleInFavoriteUseCase @Inject constructor(private val repository: FavoriteRepository) {
    operator fun invoke() = repository.getArticlesInFavorite()
}