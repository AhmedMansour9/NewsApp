package com.eaapps.search.data.di

import com.eaapps.search.data.datasource.remote.SearchRemoteDatasource
import com.eaapps.search.data.datasource.remote.SearchRemoteDatasourceImpl
import com.eaapps.search.data.datasource.remote.service.SearchApiService
import com.eaapps.search.data.repository.SearchRepositoryImpl
import com.eaapps.search.domain.repository.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object SearchModule {
    @Singleton
    @Provides
    fun provideSearchRemoteDatasource(datasource: SearchRemoteDatasourceImpl): SearchRemoteDatasource = datasource


    @Singleton
    @Provides
    fun provideSearchRepository(repository: SearchRepositoryImpl): SearchRepository = repository


    @Singleton
    @Provides
    fun provideSearchApiService(retrofit: Retrofit): SearchApiService = retrofit.create(SearchApiService::class.java)


}

