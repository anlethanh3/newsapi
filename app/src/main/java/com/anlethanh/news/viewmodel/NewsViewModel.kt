package com.anlethanh.news.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anlethanh.news.models.Article
import com.anlethanh.news.models.Error
import com.anlethanh.news.repositories.MainRepository
import io.reactivex.disposables.CompositeDisposable
import androidx.lifecycle.ViewModelProvider



class NewsViewModel(val mainRepository: MainRepository) : ViewModel() {

    val disposable: CompositeDisposable = CompositeDisposable()
    val headlines = MutableLiveData<ArrayList<Article>>()
    val isLoading = MutableLiveData<Boolean>()
    val error = MutableLiveData<Error>()

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }

    fun getHeadlines() {
        disposable.add(mainRepository.getHeadlines().subscribe(
            {
                headlines.postValue(it)
                isLoading.postValue(false)
            }, {
                error.postValue(Error.ERR_HEADLINES_API)
                isLoading.postValue(false)
            })
        )
    }

    fun getKeyword(keyword:String) {
        disposable.add(mainRepository.getKeyword(keyword).subscribe(
            {
                headlines.postValue(it)
                isLoading.postValue(false)
            }, {
                error.postValue(Error.ERR_KEYWORD_API)
                isLoading.postValue(false)
            })
        )
    }

}

class ViewModelFactory(private val repository: MainRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}