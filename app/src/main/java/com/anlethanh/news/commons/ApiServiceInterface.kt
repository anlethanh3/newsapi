package com.anlethanh.news.commons

import com.anlethanh.news.models.Article
import io.reactivex.Observable
import retrofit2.http.*
import com.anlethanh.news.models.Result

interface ApiServiceInterface {
    @GET("v2/top-headlines")
    @Headers("Content-type: application/json")
    fun getHeadlines(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("apiKey") apiKey: String
    )
            : Observable<Result<ArrayList<Article>>>

    @GET("v2/everything")
    @Headers("Content-type: application/json")
    fun getKeyword(
        @Query("q") query: String,
        @Query("from") fromDate: String,
        @Query("sortBy") sortBy: String,
        @Query("apiKey") apiKey: String
    )
            : Observable<Result<ArrayList<Article>>>
}