package com.anlethanh.news.views

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.anlethanh.news.MyApplication
import com.anlethanh.news.R
import com.anlethanh.news.commons.Helper
import com.anlethanh.news.components.DaggerDetailActivityComponent
import com.anlethanh.news.databinding.ActivityDetailBinding
import com.anlethanh.news.models.Article
import com.anlethanh.news.modules.DetailActivityModule
import com.anlethanh.news.viewmodel.DetailNewsViewModel
import com.anlethanh.news.viewmodel.DetailViewModelFactory
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import javax.inject.Inject


class DetailActivity : AppCompatActivity() {

    lateinit var factory: DetailViewModelFactory

    companion object {
        val ARTICLE = "${DetailActivity::class.java.simpleName}.ARTICLE"
    }

    lateinit var article: Article
    lateinit var viewModel: DetailNewsViewModel
    lateinit var binding: ActivityDetailBinding
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var helper: Helper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView<ActivityDetailBinding>(this, R.layout.activity_detail)
        val component = DaggerDetailActivityComponent.builder()
            .detailActivityModule(DetailActivityModule(this))
            .applicationComponent(MyApplication.get(this).component)
            .build()
        component.inject(this)
        imageLoader = ImageLoader.getInstance().apply {
            init(ImageLoaderConfiguration.createDefault(this@DetailActivity))
        }
        intent?.extras?.run {
            article = getSerializable(ARTICLE) as Article
            factory = DetailViewModelFactory(article)

            viewModel = ViewModelProviders.of(this@DetailActivity, factory)
                .get(DetailNewsViewModel::class.java)
            viewModel.news.observe(this@DetailActivity, Observer {
                binding.viewmodel = it
                binding.adTime.text = helper.getStringDate(it.publishedAt ?: "")
                imageLoader.displayImage(it.urlToImage, binding.adImage)
            })
            viewModel.getNews()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(com.anlethanh.news.R.menu.detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.visit_site -> {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(article.url)))
                true
            }
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}