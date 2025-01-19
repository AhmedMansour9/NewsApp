package com.eaapps.database.di

import android.app.Application
import android.content.Context
import androidx.room.Room.databaseBuilder
import com.eaapps.database.LocalDatabase
import com.eaapps.database.dto.FavoriteDao
import com.eaapps.database.dto.HeadlineDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providerLocalDatabase(context: Application): LocalDatabase =
        databaseBuilder(context, LocalDatabase::class.java, "news_database.db")
            .fallbackToDestructiveMigration()
            .build()


    @Provides
    @Singleton
    fun provideHeadlineDao(database: LocalDatabase): HeadlineDao = database.headlineDao()


    @Provides
    @Singleton
    fun provideFavoriteDao(database: LocalDatabase): FavoriteDao = database.favoriteDao()


}