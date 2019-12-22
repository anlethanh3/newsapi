package com.anlethanh.news.components

import com.anlethanh.news.modules.DetailActivityModule
import com.anlethanh.news.modules.DetailActivityScope
import com.anlethanh.news.views.DetailActivity
import dagger.Component

@DetailActivityScope
@Component(
    modules = arrayOf(DetailActivityModule::class),
    dependencies = arrayOf(ApplicationComponent::class)
)
interface DetailActivityComponent {
    fun inject(activity: DetailActivity)
}