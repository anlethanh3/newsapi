package com.anlethanh.news.modules

import android.content.Context
import com.anlethanh.news.commons.ApiRequest
import com.anlethanh.news.commons.Helper
import com.anlethanh.news.commons.RetrofitHelper
import com.anlethanh.news.components.MyApplicationScope
import dagger.Module
import dagger.Provides

@Module
class CommonModule(val context: Context) {

    @Provides
    @MyApplicationScope
    fun provideHelper(): Helper = Helper()

    @Provides
    @MyApplicationScope
    fun provideApiRequest(helper: Helper, retrofitHelper: RetrofitHelper) =
        ApiRequest(helper, retrofitHelper)

    @Provides
    @MyApplicationScope
    fun provideRetrofitHelper() = RetrofitHelper()
}