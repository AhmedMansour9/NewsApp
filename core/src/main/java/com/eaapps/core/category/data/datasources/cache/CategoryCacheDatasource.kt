package com.eaapps.core.category.data.datasources.cache

import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

const val CATEGORY_KEY = "categories"

interface CategoryCacheDatasource {
    suspend fun saveCategoriesToCache(categories: List<String>)
    suspend fun getCategoriesFromCache(): List<String>
}

class CategoryCacheDatasourceImpl @Inject constructor(private val shared: SharedPreferences) : CategoryCacheDatasource {
    override suspend fun saveCategoriesToCache(categories: List<String>) = withContext(Dispatchers.IO) {
        shared.edit().putStringSet(CATEGORY_KEY, categories.toSet()).apply()
    }

    override suspend fun getCategoriesFromCache(): List<String> = withContext(Dispatchers.IO) {
        shared.getStringSet(CATEGORY_KEY, emptySet())?.toList() ?: emptyList()
    }


}