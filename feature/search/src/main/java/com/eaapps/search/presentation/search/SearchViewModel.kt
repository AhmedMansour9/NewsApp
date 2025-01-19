package com.eaapps.search.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eaapps.core.base.domain.ResultSource
import com.eaapps.core.base.error.CategorizableError
import com.eaapps.core.category.domain.usecase.GetCachedCategoriesUsecase
import com.eaapps.core.category.domain.usecase.GetCategoriesUsecase
import com.eaapps.favorite.domain.entity.FavoriteEntity
import com.eaapps.favorite.domain.usecase.AddArticleToFavoriteUseCase
import com.eaapps.search.domain.entity.SearchEntity
import com.eaapps.search.domain.usecase.SearchArticleUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchArticleUsecase: SearchArticleUsecase,
    private val getCategoriesUsecase: GetCategoriesUsecase,
    private val getSelectionCategoryUseCase: GetCachedCategoriesUsecase,
    private val addArticleToFavoriteUseCase: AddArticleToFavoriteUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            val categories = getCategoriesUsecase()
            val selectionCategory = getSelectionCategoryUseCase().firstOrNull()
            val categoryPosition = categories.indexOf(selectionCategory)
            _uiState.getAndUpdate {
                it.copy(categories = categories, selectionCategoryPosition = categoryPosition, selectionCategory = selectionCategory)
            }
        }
    }

    fun searchForArticle(query: String, category: String = "") {
        viewModelScope.launch {
            searchArticleUsecase(query, category)
                .collect { r ->
                    when (r) {
                        is ResultSource.Failure -> {
                            _uiState.getAndUpdate {
                                it.copy(loading = false, data = null, error = r.throwable as CategorizableError)
                            }
                        }

                        is ResultSource.Success -> {
                            _uiState.getAndUpdate {
                                it.copy(loading = false, data = r.result, error = null)
                            }
                        }
                    }
                }
        }
    }

    fun addArticleToFavorite(entity: SearchEntity) {
        viewModelScope.launch {
            addArticleToFavoriteUseCase(entity.let {
                FavoriteEntity(
                    title = it.title,
                    date = it.date,
                    image = it.image,
                    source = it.source,
                    url = it.url,
                    shortDescription = it.shortDescription
                )
            })
        }
    }

    fun updateSelectionCategory(position: Int, category: String) {
        _uiState.getAndUpdate {
            it.copy(selectionCategoryPosition = position, selectionCategory = category)
        }
    }
}

data class SearchUiState(
    val loading: Boolean = false,
    val categories: List<String>? = null,
    val data: List<SearchEntity>? = null,
    val error: CategorizableError? = null,
    val selectionCategoryPosition: Int? = null,
    val selectionCategory: String? = null,
)