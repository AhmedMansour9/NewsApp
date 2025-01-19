package com.eaapps.core.country.data.repository

import com.eaapps.core.country.data.datasources.cache.CountryCacheDatasource
import com.eaapps.core.country.domain.entity.CountryEntity
import com.eaapps.core.country.domain.repository.CountryRepository
import javax.inject.Inject

class CountryRepositoryImpl @Inject constructor(private val cache: CountryCacheDatasource) : CountryRepository {
    override suspend fun saveSelectedCountry(country: String) = cache.saveCountryToCache(country)

    override suspend fun getCountryFromCache(): String? = cache.getCountryFromCache()

    override suspend fun fetchCountries(): List<CountryEntity> = listOf(
        CountryEntity("Argentina", "ar"),
        CountryEntity("Australia", "au"),
        CountryEntity("Belgium", "be"),
        CountryEntity("Brazil", "br"),
        CountryEntity("Canada", "ca"),
        CountryEntity("Switzerland", "ch"),
        CountryEntity("China", "cn"),
        CountryEntity("Colombia", "co"),
        CountryEntity("Cuba", "cu"),
        CountryEntity("Czech Republic", "cz"),
        CountryEntity("Germany", "de"),
        CountryEntity("Egypt", "eg"),
        CountryEntity("France", "fr"),
        CountryEntity("United Kingdom", "gb"),
        CountryEntity("Greece", "gr"),
        CountryEntity("Hong Kong", "hk"),
        CountryEntity("Hungary", "hu"),
        CountryEntity("Indonesia", "id"),
        CountryEntity("Ireland", "ie"),
        CountryEntity("India", "in"),
        CountryEntity("Lithuania", "lt")
    )
}