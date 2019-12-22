package com.anlethanh.news.commons

import io.reactivex.Observable
import javax.inject.Inject
import com.anlethanh.news.models.Result

class ApiRequest
@Inject
constructor(val helper: Helper, val retrofitHelper: RetrofitHelper) {

    private fun <T> Observable<Result<T>>.getObjectResult() = flatMap {
        it.articles?.let {
            Observable.just(it)
        } ?: run {
            Observable.error<T>(Throwable(it.status ?: "error"))
        }
    }

    fun getHeadlines() =
            retrofitHelper.getApiService()
                    .getHeadlines("us","business",helper.apiKey)
                    .getObjectResult()

    fun getKeyword(keyword:String) =
        retrofitHelper.getApiService()
            .getKeyword(keyword,helper.getDateAfter(7),"publishedAt",helper.apiKey)
            .getObjectResult()
}