package com.anlethanh.news.components

import com.anlethanh.news.MyApplication
import com.anlethanh.news.commons.Helper
import com.anlethanh.news.commons.RetrofitHelper
import com.anlethanh.news.modules.CommonModule
import dagger.Component
import javax.inject.Scope

@Component(modules = arrayOf(CommonModule::class))
@MyApplicationScope
interface ApplicationComponent {
    fun inject(myApplication: MyApplication)
    fun getHelper(): Helper
    fun getRetrofitHelper(): RetrofitHelper
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class MyApplicationScope