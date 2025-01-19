package com.eaapps.database.dto

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eaapps.database.entities.HeadlineArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HeadlineDao {

    @Query("SELECT * FROM top_headline_article")
    fun getTopHeadline(): Flow<List<HeadlineArticleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTopHeadline(articles: List<HeadlineArticleEntity>)

    @Query("delete from top_headline_article")
    suspend fun clearTopHeadline()
}