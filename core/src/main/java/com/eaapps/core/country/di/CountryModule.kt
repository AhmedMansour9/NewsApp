package com.eaapps.core.country.di

import com.eaapps.core.country.data.datasources.cache.CountryCacheDatasource
import com.eaapps.core.country.data.datasources.cache.CountryCacheDatasourceImpl
import com.eaapps.core.country.data.repository.CountryRepositoryImpl
import com.eaapps.core.country.domain.repository.CountryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object CountryModule {
    @Singleton
    @Provides
    fun provideCountryCacheDatasource(datasource: CountryCacheDatasourceImpl): CountryCacheDatasource = datasource


    @Singleton
    @Provides
    fun provideCountryRepository(repository: CountryRepositoryImpl): CountryRepository = repository


}