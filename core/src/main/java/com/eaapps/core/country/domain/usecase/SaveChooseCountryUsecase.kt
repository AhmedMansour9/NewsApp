package com.eaapps.core.country.domain.usecase

import com.eaapps.core.country.domain.repository.CountryRepository
import javax.inject.Inject

class SaveChooseCountryUsecase @Inject constructor(private val repository: CountryRepository) {
    suspend operator fun invoke(country:String) = repository.saveSelectedCountry(country)
}