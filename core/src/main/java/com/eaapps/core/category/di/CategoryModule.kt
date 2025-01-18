package com.eaapps.core.category.di

import com.eaapps.core.category.data.datasources.cache.CategoryCacheDatasource
import com.eaapps.core.category.data.datasources.cache.CategoryCacheDatasourceImpl
import com.eaapps.core.category.data.repository.CategoryRepositoryImpl
import com.eaapps.core.category.domain.repository.CategoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object CategoryModule {
    @Singleton
    @Provides
    fun provideCategoryCacheDatasource(datasource: CategoryCacheDatasourceImpl): CategoryCacheDatasource = datasource


    @Singleton
    @Provides
    fun provideCategoryRepository(repository: CategoryRepositoryImpl): CategoryRepository = repository

}