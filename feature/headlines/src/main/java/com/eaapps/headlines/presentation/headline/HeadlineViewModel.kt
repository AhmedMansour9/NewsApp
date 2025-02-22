package com.eaapps.headlines.presentation.headline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eaapps.core.base.domain.FlowResultSource
import com.eaapps.core.base.error.CategorizableError
import com.eaapps.favorite.domain.entity.FavoriteEntity
import com.eaapps.favorite.domain.usecase.AddArticleToFavoriteUseCase
import com.eaapps.headlines.domain.entity.HeadlineEntity
import com.eaapps.headlines.domain.usecase.GetHeadlineUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeadlineViewModel @Inject constructor(
    private val getHeadlineUsecase: GetHeadlineUsecase,
    private val addArticleToFavoriteUseCase: AddArticleToFavoriteUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HeadlineUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadHeadlines()
    }

    private fun loadHeadlines() {
        viewModelScope.launch {
            getHeadlineUsecase()
                .collect { r ->
                    when (r) {
                        is FlowResultSource.Loading -> {
                            _uiState.getAndUpdate {
                                it.copy(loading = true, data = r.data)
                            }
                        }

                        is FlowResultSource.Failure -> {
                            _uiState.getAndUpdate {
                                it.copy(loading = false, data = r.data, error = r.throwable as CategorizableError)
                            }
                        }

                        is FlowResultSource.Success -> {
                            _uiState.getAndUpdate {
                                it.copy(loading = false, data = r.result, error = null)
                            }
                        }
                    }
                }
        }
    }

    fun addArticleToFavorite(entity: HeadlineEntity) {
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


}

data class HeadlineUiState(val loading: Boolean = false, val data: List<HeadlineEntity>? = null, val error: CategorizableError? = null)