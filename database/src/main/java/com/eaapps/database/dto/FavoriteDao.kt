package com.eaapps.database.dto

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eaapps.database.entities.FavoriteLocalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorite_article")
    fun getFavoriteArticles(): Flow<List<FavoriteLocalEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteStatus(entity: FavoriteLocalEntity)

    @Query("DELETE FROM favorite_article WHERE url = :url")
    suspend fun removeArticleFromFavorite(url: String)


}