package com.eaapps.favorite.data.di

import com.eaapps.favorite.data.datasource.local.FavoriteLocalDatasource
import com.eaapps.favorite.data.datasource.local.FavoriteLocalDatasourceImpl
import com.eaapps.favorite.data.repository.FavoriteRepositoryImpl
import com.eaapps.favorite.domain.repository.FavoriteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object FavoriteModule {
    @Singleton
    @Provides
    fun provideFavoriteLocalDatasource(datasource: FavoriteLocalDatasourceImpl): FavoriteLocalDatasource = datasource


    @Singleton
    @Provides
    fun provideFavoriteRepository(repository: FavoriteRepositoryImpl): FavoriteRepository = repository

}

