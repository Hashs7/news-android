package me.sebhernoux.news.models

data class ArticleResponse(val status: String, val totalResults: Int, val articles: List<Article>)