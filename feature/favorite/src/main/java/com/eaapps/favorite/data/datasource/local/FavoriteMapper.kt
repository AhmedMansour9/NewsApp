package com.eaapps.favorite.data.datasource.local

import com.eaapps.database.entities.FavoriteLocalEntity
import com.eaapps.favorite.domain.entity.FavoriteEntity

fun FavoriteLocalEntity.toDomainEntity() = FavoriteEntity(
    title = title,
    date = date,
    image = image,
    source = source,
    url = url,
    shortDescription = shortDescription
)

fun FavoriteEntity.toLocalEntity() = FavoriteLocalEntity(
    title = title,
    date = date,
    image = image,
    source = source,
    url = url,
    shortDescription = shortDescription
)

