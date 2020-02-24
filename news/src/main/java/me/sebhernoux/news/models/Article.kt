package me.sebhernoux.news.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Article(
    val title: String,
    val description: String,
    val content: String,
    val url: String,
    val urlToImage: String,
    val author: String,
    val publishedAt: String,
    val source: ArticleSource,
    var favorite: Boolean = false
) : Parcelable

@Parcelize
data class ArticleSource(val id: String, val name: String): Parcelable
