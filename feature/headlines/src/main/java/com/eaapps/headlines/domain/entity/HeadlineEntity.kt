package com.eaapps.headlines.domain.entity

data class HeadlineEntity(
    val title: String,
    val date: String,
    val image: String?,
    val source: String,
    val url: String,
    val shortDescription: String,
    val favorite: Boolean = false
)
