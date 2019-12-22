package com.anlethanh.news.repositories

import com.anlethanh.news.commons.ApiRequest
import com.anlethanh.news.commons.Helper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainRepository
@Inject
constructor(val apiRequest: ApiRequest, val helper: Helper) {
    fun getHeadlines() = apiRequest.getHeadlines()
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())

    fun getKeyword(keyword: String) = apiRequest.getKeyword(keyword)
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
}