package com.eaapps.onboarding.di

import com.eaapps.onboarding.data.datasources.OnBoardingCacheDatasource
import com.eaapps.onboarding.data.datasources.OnBoardingCacheDatasourceImpl
import com.eaapps.onboarding.data.repository.OnBoardingRepositoryImpl
import com.eaapps.onboarding.domain.repository.OnBoardingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object OnBoardingModule {
    @Singleton
    @Provides
    fun provideOnBoardingCacheDatasource(datasource: OnBoardingCacheDatasourceImpl): OnBoardingCacheDatasource = datasource


    @Singleton
    @Provides
    fun provideOnBoardingRepository(repository: OnBoardingRepositoryImpl): OnBoardingRepository = repository

}