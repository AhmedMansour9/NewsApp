package com.eaapps.search.domain.usecase

import com.eaapps.core.base.domain.ResultSource
import com.eaapps.core.base.error.ValidateError
import com.eaapps.core.category.domain.repository.CategoryRepository
import com.eaapps.favorite.domain.repository.FavoriteRepository
import com.eaapps.search.domain.entity.SearchEntity
import com.eaapps.search.domain.repository.SearchRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject


class SearchArticleUsecase @Inject constructor(
    private val repository: SearchRepository,
    private val categoryRepository: CategoryRepository,
    private val favoriteRepository: FavoriteRepository,
) {
    suspend operator fun invoke(query: String, category: String): Flow<ResultSource<List<SearchEntity>>> {
        val selectedCategory = categoryRepository.getCategoryFromCache()

        return if (selectedCategory.isNotEmpty()) {
            val articles = kotlin.runCatching {
                repository.searchArticle(query, category.ifEmpty { selectedCategory.first() })
            }.getOrElse {
                flow {
                    emit(ResultSource.Failure(it))
                }
                null
            }
            flowOf(articles).combine(favoriteRepository.getArticlesInFavorite()) { articleList, favoriteArticles ->
                ResultSource.Success(articleList?.map { article ->
                    article.copy(favorite = favoriteArticles.any { it.url == article.url })
                })
            }

        } else {
            flow {
                emit(ResultSource.Failure(ValidateError.CountryMissing))
            }
        }
    }
}


