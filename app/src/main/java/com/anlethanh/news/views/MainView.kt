package com.anlethanh.news.views

import com.anlethanh.news.models.Article
import com.anlethanh.news.models.Error

interface MainView {
    fun onError(error: Error)
    fun onOpenHeadlines(data:ArrayList<Article>)
    fun onOpenKeyword(data:ArrayList<Article>)
}