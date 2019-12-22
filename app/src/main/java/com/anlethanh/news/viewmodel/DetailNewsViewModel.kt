package com.anlethanh.news.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anlethanh.news.models.Article
import androidx.lifecycle.ViewModelProvider


class DetailNewsViewModel(val article: Article) : ViewModel() {

    private val _news = MutableLiveData<Article>()

    val news = _news as LiveData<Article>

    fun getNews(){
        _news.postValue(article)
    }
}

class DetailViewModelFactory(private val article: Article) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailNewsViewModel::class.java)) {
            return DetailNewsViewModel(article) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}