package com.eaapps.search.data.datasource.remote.model

import com.eaapps.core.base.extensions.toDateFormatDMMMYYYY
import com.eaapps.search.domain.entity.SearchEntity

fun ArticleDto.toDomainEntity() = SearchEntity(
    title = title,
    date = publishedAt.toDateFormatDMMMYYYY(),
    source = source.name,
    url = url,
    image = urlToImage,
    shortDescription = description
)