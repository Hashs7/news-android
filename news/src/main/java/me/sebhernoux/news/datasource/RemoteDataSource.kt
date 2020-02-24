package me.sebhernoux.news.datasource

import me.sebhernoux.news.BuildConfig
import me.sebhernoux.news.models.Article
import me.sebhernoux.news.services.ArticleService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RemoteDataSource {
    private val service: ArticleService
    private val API_KEY: String = "371e4ba923294ab4a3545c8d2ddf0b31"

    init {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
        val retrofit = Retrofit.Builder().apply {
            //Ajouter un converter pour JSON
            addConverterFactory(GsonConverterFactory.create()).client(client)
            baseUrl("https://newsapi.org/v2/")

        }.build()
        //Créer une instance du service
        service = retrofit.create(ArticleService::class.java)
    }

    fun getRemoteArticles(): List<Article> {
        val result = service.getArticles(API_KEY).execute()
        return if (result.isSuccessful) {
            result.body()?.articles ?: emptyList()
        } else {
            emptyList()
        }
    }
}