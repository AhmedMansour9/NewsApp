package com.eaapps.onboarding.presentation.onBoarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eaapps.core.category.domain.usecase.GetCategoriesUsecase
import com.eaapps.core.category.domain.usecase.SaveSelectCategoryUsecase
import com.eaapps.core.country.domain.entity.CountryEntity
import com.eaapps.core.country.domain.usecase.GetCountriesUsecase
import com.eaapps.core.country.domain.usecase.SaveChooseCountryUsecase
import com.eaapps.onboarding.domain.usecases.MarkOnBoardingAsShownUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoardingViewModel @Inject constructor(
    private val getCountriesUsecase: GetCountriesUsecase,
    private val getCategoriesUsecase: GetCategoriesUsecase,
    private val saveCountryUsecase: SaveChooseCountryUsecase,
    private val saveSelectCategoryUsecase: SaveSelectCategoryUsecase,
    private val markOnBoardingAsShownUseCase: MarkOnBoardingAsShownUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(BoardingUiState())
    val uiState = _uiState.asStateFlow()


    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val categories = getCategoriesUsecase()
            val countries = getCountriesUsecase()
            _uiState.getAndUpdate {
                it.copy(categories = categories, countries = countries)
            }
        }
    }

    fun saveChooseCountry(country: String) {
        viewModelScope.launch {
            _uiState.getAndUpdate {
                it.copy(
                    error = null, selectedCountry = country, enableFinish = (country.isNotEmpty() && it.selectedCategories != null && it.selectedCategories.size >= 3)

                )
            }
            saveCountryUsecase(country)
        }
    }

    fun saveSelectCategory(categoryList: List<String>) {
        viewModelScope.launch {
            _uiState.getAndUpdate {
                it.copy(
                    error = null, selectedCategories = categoryList, enableFinish = (it.selectedCountry != null && categoryList.size >= 3)
                )
            }
            saveSelectCategoryUsecase(categoryList)

        }
    }

    fun finishSetup() {
        viewModelScope.launch {
            markOnBoardingAsShownUseCase()
        }
    }
}

data class BoardingUiState(
    val categories: List<String>? = null,
    val countries: List<CountryEntity>? = null,
    val selectedCategories: List<String>? = null,
    val selectedCountry: String? = null,
    val error: String? = null,
    val enableFinish: Boolean = false,
)