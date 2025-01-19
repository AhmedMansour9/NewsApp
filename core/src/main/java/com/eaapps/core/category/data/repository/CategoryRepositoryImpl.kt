package com.eaapps.core.category.data.repository

import com.eaapps.core.category.data.datasources.cache.CategoryCacheDatasource
import com.eaapps.core.category.domain.repository.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(private val cache: CategoryCacheDatasource) : CategoryRepository {

    override suspend fun saveCategories(categories: List<String>) = cache.saveCategoriesToCache(categories)

    override suspend fun getCategoryFromCache(): List<String> = cache.getCategoriesFromCache()

    override suspend fun fetchCategories(): List<String> =
        arrayListOf("business", "entertainment", "general", "health", "science", "sports", "technology")
}