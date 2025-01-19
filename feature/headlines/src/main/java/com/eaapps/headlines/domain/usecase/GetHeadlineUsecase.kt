package com.eaapps.headlines.domain.usecase

import com.eaapps.core.base.domain.FlowResultSource
import com.eaapps.core.base.error.ValidateError
import com.eaapps.core.category.domain.repository.CategoryRepository
import com.eaapps.core.country.domain.repository.CountryRepository
import com.eaapps.headlines.domain.entity.HeadlineEntity
import com.eaapps.headlines.domain.repository.HeadlineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetHeadlineUsecase @Inject constructor(
    private val repository: HeadlineRepository,
    private val countryRepository: CountryRepository,
    private val categoryRepository: CategoryRepository,
) {
    suspend operator fun invoke(): Flow<FlowResultSource<List<HeadlineEntity>>> {
        val selectedCountry = countryRepository.getCountryFromCache()
        val selectedCategory = categoryRepository.getCategoryFromCache()
        return if (selectedCountry != null && selectedCategory.isNotEmpty()) {
            repository.getHeadlines(selectedCountry, selectedCategory.first())
        } else {
            flow {
                emit(
                    FlowResultSource.Failure(
                        ValidateError.CountryMissing, null
                    )
                )
            }
        }
    }
}
