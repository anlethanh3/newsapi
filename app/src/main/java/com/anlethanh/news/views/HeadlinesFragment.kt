package com.anlethanh.news.views

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anlethanh.news.R
import com.anlethanh.news.databinding.HeadlineItemBinding
import com.anlethanh.news.models.Article
import com.anlethanh.news.viewmodel.NewsViewModel
import com.anlethanh.news.viewmodel.ViewModelFactory
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import kotlinx.android.synthetic.main.recycleview.*
import kotlinx.android.synthetic.main.recycleview.view.*

class HeadlinesFragment(val factory: ViewModelFactory) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.recycleview, container, false)
    }

    lateinit var viewModel: NewsViewModel
    lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(NewsViewModel::class.java)
        viewModel.headlines.observe(this, Observer {
            recycler_view.adapter = HeadlineAdapter(it, imageLoader)
        })
        viewModel.error.observe(this, Observer {
            Toast.makeText(this.context, "get error headlines", Toast.LENGTH_LONG).show()
        })
        imageLoader = ImageLoader.getInstance().apply {
            init(ImageLoaderConfiguration.createDefault(this@HeadlinesFragment.context))
        }
        viewModel.isLoading.observe(this, Observer {
            view?.run {
                swipe_refresh.isRefreshing = it
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewManager = LinearLayoutManager(this.context)
        recycler_view.layoutManager = viewManager
        view.apply {
            this.swipe_refresh.setOnRefreshListener {
                viewModel.getHeadlines()
            }
        }
        viewModel.getHeadlines()
    }
}

class HeadlineAdapter(val data: ArrayList<Article>, val imageLoader: ImageLoader) :
    RecyclerView.Adapter<HeadlineAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = HeadlineItemBinding.inflate(layoutInflater)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class ViewHolder(val binding: HeadlineItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Article) {
            binding.viewmodel = data
            data.urlToImage?.run {
                imageLoader.displayImage(this, binding.hlImage)
            }

            binding.root.setOnClickListener {
                binding.root.context.run {
                    startActivity(
                        Intent(
                            this,
                            DetailActivity::class.java
                        ).putExtra(DetailActivity.ARTICLE, data)
                    )
                }
            }
        }
    }
}
