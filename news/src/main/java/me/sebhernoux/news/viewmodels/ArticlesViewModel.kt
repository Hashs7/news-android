package me.sebhernoux.news.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.sebhernoux.news.models.Article
import me.sebhernoux.news.repository.ArticleRepository

class ArticlesViewModel : ViewModel() {
    private val repository: ArticleRepository = ArticleRepository()
    private val _listArticles = MutableLiveData<List<Article>>()
    val listArticles: LiveData<List<Article>>
        get() = _listArticles

    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getArticles()
            _listArticles.postValue(result)
        }
    }

    fun favArticle(article: Article) {
        article.favorite = !article.favorite
    }
}