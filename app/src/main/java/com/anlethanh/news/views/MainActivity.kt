package com.anlethanh.news.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProviders
import com.anlethanh.news.MyApplication
import com.anlethanh.news.R
import com.anlethanh.news.commons.Helper
import com.anlethanh.news.components.DaggerApplicationComponent
import com.anlethanh.news.components.DaggerMainActivityComponent
import com.anlethanh.news.models.Article
import com.anlethanh.news.models.Error
import com.anlethanh.news.modules.CommonModule
import com.anlethanh.news.modules.MainActivityModule
import com.anlethanh.news.viewmodel.NewsViewModel
import com.anlethanh.news.viewmodel.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory

    @Inject
    lateinit var helper: Helper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val component = DaggerMainActivityComponent.builder()
            .mainActivityModule(MainActivityModule(this))
            .applicationComponent(MyApplication.get(this).component)
            .build()
        component.inject(this)
        am_tabs.setupWithViewPager(am_pager)
        am_pager.adapter = PagerAdapter(supportFragmentManager, factory, helper)
        am_tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
            }
        })
        am_tabs.getTabAt(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_home)
        am_tabs.getTabAt(1)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_rss)
        am_tabs.getTabAt(2)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_person)
    }
}

class PagerAdapter(fm: FragmentManager, val factory: ViewModelFactory, val helper: Helper) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? = when (position) {
        0 -> HeadlinesFragment(factory)
        1 -> NewsFragment(factory)
        2 -> ProfileFragment(helper)
        else -> null
    }

    override fun getCount(): Int = 3
}
