package com.eaapps.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.eaapps.database.dto.FavoriteDao
import com.eaapps.database.dto.HeadlineDao
import com.eaapps.database.entities.FavoriteLocalEntity
import com.eaapps.database.entities.HeadlineArticleEntity

@Database(entities = [HeadlineArticleEntity::class, FavoriteLocalEntity::class], version = 1, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun headlineDao(): HeadlineDao
    abstract fun favoriteDao(): FavoriteDao

}