package com.eaapps.favorite.domain.entity

data class FavoriteEntity(
    val title: String,
    val date: String,
    val image: String?,
    val source: String,
    val url: String,
    val shortDescription: String
)
