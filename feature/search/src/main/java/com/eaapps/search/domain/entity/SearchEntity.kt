package com.eaapps.search.domain.entity


data class SearchEntity(
    val title: String,
    val date: String,
    val image: String?,
    val source: String,
    val url: String,
    val shortDescription: String,
    val favorite: Boolean = false,
)
