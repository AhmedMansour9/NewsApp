package com.eaapps.headlines.data.datasource.local

import com.eaapps.core.base.extensions.toDateFormatDMMMYYYY
import com.eaapps.database.entities.HeadlineArticleEntity
import com.eaapps.headlines.domain.entity.HeadlineEntity


fun HeadlineArticleEntity.toDomainEntity() = HeadlineEntity(
    title = title,
    date = date.toDateFormatDMMMYYYY(),
    source = source,
    url = url,
    image = image,
    shortDescription = shortDescription
)