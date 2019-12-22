package com.anlethanh.news.commons
import android.util.Log
import okhttp3.OkHttpClient
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit

class RetrofitHelper() {
    private val TAG = RetrofitHelper::class.java.simpleName
    private val AUTHORIATION = "Authorization"

    private fun createOkHttpClient() = OkHttpClient.Builder()
            .addInterceptor {
                val request = it.request().newBuilder()
//                        .addHeader("Content-Type", "application/json; charset=utf-8")
                    .build()

                Log.d(TAG, "----- START retrofit -----")
                Log.d(TAG, "url: ${request.url()}")
                Log.d(TAG, "method: ${request.method()}")
                Log.d(TAG, "authorization: ${request.header(AUTHORIATION)}")
                val buffer = Buffer()
                request.body()?.let {
                    it.writeTo(buffer)
                    val body = buffer.readUtf8()
                    Log.d(TAG, "request json: $body")
                }
                val response = it.proceed(request) //perform request, here original request will be executed

                Log.d(TAG, "status: ${response.code()}")
//                Log.d(TAG, "response json: ${response.body()?.string()}")
                Log.d(TAG, "----- END retrofit -----")
                response
            }
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

    private fun createRetrofit(url: String): Retrofit {
        return Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(createOkHttpClient()) // <- add this
                .build()
    }

    fun getApiService(): ApiServiceInterface {
        val url = "https://newsapi.org/"
        val retrofit = createRetrofit(url)
        return retrofit.create(ApiServiceInterface::class.java)
    }
}