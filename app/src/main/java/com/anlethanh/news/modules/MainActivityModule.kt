package com.anlethanh.news.modules

import android.app.Activity
import com.anlethanh.news.commons.ApiRequest
import com.anlethanh.news.commons.Helper
import com.anlethanh.news.repositories.MainRepository
import com.anlethanh.news.viewmodel.NewsViewModel
import com.anlethanh.news.viewmodel.ViewModelFactory
import com.anlethanh.news.views.MainActivity
import dagger.Module
import dagger.Provides
import javax.inject.Scope

@Module
class MainActivityModule(val mainActivity: MainActivity) {

    @Provides
    @MainActivityScope
    fun provideActivity(): Activity = mainActivity

    @Provides
    @MainActivityScope
    fun provideMainRepository(apiRequest: ApiRequest, helper: Helper): MainRepository =
        MainRepository(apiRequest, helper)

    @Provides
    @MainActivityScope
    fun provideViewModelFactory(mainRepository: MainRepository): ViewModelFactory =
        ViewModelFactory(mainRepository)
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class MainActivityScope