package com.anlethanh.news.modules

import android.app.Activity
import com.anlethanh.news.views.DetailActivity
import dagger.Module
import dagger.Provides
import javax.inject.Scope

@Module
class DetailActivityModule(val activity: DetailActivity) {

    @Provides
    @DetailActivityScope
    fun provideActivity(): Activity = activity
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class DetailActivityScope