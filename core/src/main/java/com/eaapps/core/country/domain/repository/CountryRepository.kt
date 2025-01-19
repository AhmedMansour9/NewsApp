package com.eaapps.core.country.domain.repository

import com.eaapps.core.country.domain.entity.CountryEntity

interface CountryRepository {

    suspend fun saveSelectedCountry(country: String)

    suspend fun getCountryFromCache():String?

    suspend fun fetchCountries(): List<CountryEntity>


}