package com.anlethanh.news.models

class Result<T> {
    var status: String? = null
    var totalResults: Int = 0
    var articles: T?= null
}