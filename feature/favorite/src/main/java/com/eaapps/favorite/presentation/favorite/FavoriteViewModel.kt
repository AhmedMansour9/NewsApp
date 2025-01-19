package com.eaapps.favorite.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eaapps.favorite.domain.entity.FavoriteEntity
import com.eaapps.favorite.domain.usecase.AddArticleToFavoriteUseCase
import com.eaapps.favorite.domain.usecase.GetArticleInFavoriteUseCase
import com.eaapps.favorite.domain.usecase.RemoveArticleToFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val removeArticleToFavoriteUseCase: RemoveArticleToFavoriteUseCase,
    private val getArticleInFavoriteUseCase: GetArticleInFavoriteUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoriteUiState(loading = true))
    val uiState = _uiState.asStateFlow()

    init {
        loadFavorite()
    }

    private fun loadFavorite() {
        viewModelScope.launch {
            getArticleInFavoriteUseCase().collectLatest { r ->
                _uiState.getAndUpdate {
                    it.copy(loading = false, data = r)
                }
            }
        }
    }

    fun removeArticleFromFavorite(url: String) {
        viewModelScope.launch {
            removeArticleToFavoriteUseCase(url)
        }
    }

}

data class FavoriteUiState(val loading: Boolean = false, val data: List<FavoriteEntity>? = null)