package me.sebhernoux.news.repository

import me.sebhernoux.news.datasource.RemoteDataSource
import me.sebhernoux.news.models.Article

class ArticleRepository {
    private val online = RemoteDataSource()

    fun getArticles(): List<Article> {
        return online.getRemoteArticles()
    }
}