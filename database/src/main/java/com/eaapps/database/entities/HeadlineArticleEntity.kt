package com.eaapps.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "top_headline_article")
data class HeadlineArticleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val date: String,
    val image: String?,
    val source: String,
    val url: String,
    val shortDescription: String,
    val favorite: Boolean = false
)