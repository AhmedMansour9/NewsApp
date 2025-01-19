package com.eaapps.headlines.data.di

import com.eaapps.headlines.data.datasource.local.HeadlineLocalDatasource
import com.eaapps.headlines.data.datasource.local.HeadlineLocalDatasourceImpl
import com.eaapps.headlines.data.datasource.remote.HeadlineRemoteDatasource
import com.eaapps.headlines.data.datasource.remote.HeadlineRemoteDatasourceImpl
import com.eaapps.headlines.data.datasource.remote.service.HeadlineApiService
import com.eaapps.headlines.data.repository.HeadlineRepositoryImpl
import com.eaapps.headlines.domain.repository.HeadlineRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object HeadlineModule {
    @Singleton
    @Provides
    fun provideHeadlineRemoteDatasource(datasource: HeadlineRemoteDatasourceImpl): HeadlineRemoteDatasource = datasource

 @Singleton
    @Provides
    fun provideHeadlineLocalDatasource(datasource: HeadlineLocalDatasourceImpl): HeadlineLocalDatasource = datasource


    @Singleton
    @Provides
    fun provideHeadlineRepository(repository: HeadlineRepositoryImpl): HeadlineRepository = repository



    @Singleton
    @Provides
    fun provideHeadlineApiService(retrofit: Retrofit): HeadlineApiService = retrofit.create(HeadlineApiService::class.java)


}

