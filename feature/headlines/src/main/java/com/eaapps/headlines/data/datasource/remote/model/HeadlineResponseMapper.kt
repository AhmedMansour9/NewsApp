package com.eaapps.headlines.data.datasource.remote.model

import com.eaapps.database.entities.HeadlineArticleEntity
import com.eaapps.headlines.domain.entity.HeadlineEntity


fun ArticleDto.toDomainEntity() = HeadlineEntity(
    title = title,
    date = publishedAt,
    source = source.name,
    url = url,
    image = urlToImage,
    shortDescription = description
)

fun ArticleDto.toLocalEntity() = HeadlineArticleEntity(
    title = title,
    date = publishedAt,
    source = source.name,
    url = url,
    image = urlToImage,
    shortDescription = description
)


