package com.eaapps.headlines.data.datasource.remote.model

data class HeadlineResponseDto(
    val totalResult:Int,
    val status:String,
    val articles:List<ArticleDto>
)

data class ArticleDto(
    val author:String,
    val title:String,
    val description:String?,
    val url:String,
    val urlToImage:String?,
    val publishedAt:String,
    val content:String?,
    val source:SourceDto
)

data class SourceDto(
    val id:String?,
    val name:String
)

