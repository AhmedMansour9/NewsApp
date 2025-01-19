package com.eaapps.core.country.data.datasources.cache

import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

const val COUNTRY_KEY = "country"

interface CountryCacheDatasource {
    suspend fun saveCountryToCache(country: String)
    suspend fun getCountryFromCache(): String?
}

class CountryCacheDatasourceImpl @Inject constructor(private val shared: SharedPreferences) : CountryCacheDatasource {
    override suspend fun saveCountryToCache(country: String) = withContext(Dispatchers.IO) {
        shared.edit().putString(COUNTRY_KEY, country).apply()
    }

    override suspend fun getCountryFromCache(): String? = withContext(Dispatchers.IO) {
        shared.getString(COUNTRY_KEY, null)
    }


}