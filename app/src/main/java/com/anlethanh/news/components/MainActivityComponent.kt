package com.anlethanh.news.components

import com.anlethanh.news.modules.MainActivityScope
import com.anlethanh.news.modules.MainActivityModule
import com.anlethanh.news.views.MainActivity
import dagger.Component

@MainActivityScope
@Component(modules = arrayOf(MainActivityModule::class), dependencies = arrayOf(ApplicationComponent::class))
interface MainActivityComponent {
    fun inject(activity: MainActivity)
}