package com.anlethanh.news.models

import java.io.Serializable

class Article: Serializable{
    var source: Source? = null
    var author: String? = null
    var title: String? = null
    var description: String? = null
    var url: String? = null
    var urlToImage: String? = null
    var publishedAt: String? = null
    var content: String? = null
}