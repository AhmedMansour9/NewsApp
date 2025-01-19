package com.eaapps.core.category.domain.usecase

import com.eaapps.core.category.domain.repository.CategoryRepository
import javax.inject.Inject

class GetCachedCategoriesUsecase @Inject constructor(private val repository: CategoryRepository) {
    suspend operator fun invoke() = repository.getCategoryFromCache()
}