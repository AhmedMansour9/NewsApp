package com.eaapps.core.category.domain.repository

interface CategoryRepository {

    suspend fun saveCategories(categories: List<String>)

    suspend fun getCategoryFromCache(): List<String>

    suspend fun fetchCategories():List<String>

}