package me.sebhernoux.news.services

import me.sebhernoux.news.models.ArticleResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleService {
    @GET("everything")
    fun getArticles(@Query("apiKey") key: String, @Query("q") q: String = "bitcoin"): Call<ArticleResponse>
}