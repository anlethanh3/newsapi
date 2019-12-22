package com.anlethanh.news

import android.app.Activity
import android.app.Application
import android.app.Service
import android.content.Context
import android.content.res.Configuration
import androidx.multidex.MultiDex
import com.anlethanh.news.commons.Helper
import com.anlethanh.news.components.ApplicationComponent
import com.anlethanh.news.components.DaggerApplicationComponent
import com.anlethanh.news.modules.CommonModule
import java.util.*
import javax.inject.Inject

/**
 * Created by An on 6/30/2017.
 */

class MyApplication : Application() {

    lateinit var component: ApplicationComponent
    @Inject
    lateinit var helper: Helper

    override fun onCreate() {
        super.onCreate()
        component = DaggerApplicationComponent.builder()
                .commonModule(CommonModule(this))
                .build()
        component.inject(this)

        helper.load(this)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    companion object {
        fun get(activity: Activity) = activity.application as MyApplication
        fun get(service: Service) = service.application as MyApplication
    }
}
