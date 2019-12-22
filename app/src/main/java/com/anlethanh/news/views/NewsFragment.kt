package com.anlethanh.news.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.anlethanh.news.R
import com.anlethanh.news.viewmodel.NewsViewModel
import com.anlethanh.news.viewmodel.ViewModelFactory
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import kotlinx.android.synthetic.main.news.*
import kotlinx.android.synthetic.main.recycleview.*
import kotlinx.android.synthetic.main.recycleview.view.*

class NewsFragment(val factory: ViewModelFactory) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.news, container, false)
    }

    lateinit var viewModel: NewsViewModel
    lateinit var imageLoader: ImageLoader
    var keywords: Array<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(NewsViewModel::class.java)
        viewModel.headlines.observe(this, Observer {
            recycler_view.adapter = HeadlineAdapter(it, imageLoader)
        })
        viewModel.error.observe(this, Observer {
            Toast.makeText(this.context, "get error keyword api", Toast.LENGTH_LONG).show()
        })
        imageLoader = ImageLoader.getInstance().apply {
            init(ImageLoaderConfiguration.createDefault(this@NewsFragment.context))
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
                keywords?.run {
                    viewModel.getKeyword(this[spinner.selectedItemPosition])
                }
            }
        }

        ArrayAdapter.createFromResource(
            this.context!!,
            R.array.keyword_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    keywords?.run {
                        viewModel.getKeyword(this[p2])
                    }
                }

            }
            spinner.adapter = adapter
        }

        context?.resources?.getStringArray(R.array.keyword_array)?.run {
            keywords = this
            viewModel.getKeyword(this.first())
        }

    }
}