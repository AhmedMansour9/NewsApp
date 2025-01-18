package com.eaapps.core.country.domain.usecase

import com.eaapps.core.country.domain.repository.CountryRepository
import javax.inject.Inject

class GetCountriesUsecase @Inject constructor(private val repository: CountryRepository) {
    suspend operator fun invoke() = repository.fetchCountries()
}