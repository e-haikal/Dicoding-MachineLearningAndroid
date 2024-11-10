package com.dicoding.asclepius.view.news

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.data.remote.ArticlesItem
import com.dicoding.asclepius.data.remote.Repository
import com.dicoding.asclepius.data.remote.Result

// ViewModel for managing news data and coordinating with Repository
class NewsViewModel(private val repository: Repository): ViewModel() {

    // Holds the list of news articles to be observed by the UI
    private val _listNews = MutableLiveData<List<ArticlesItem>>()
    val listNews: LiveData<List<ArticlesItem>> = _listNews

    // Manages loading state to show/hide progress indicators in the UI
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // Initializes by calling getNews() to load news data when ViewModel is created
    init {
        getNews()
    }

    // Fetches news from Repository and updates LiveData based on Result
    private fun getNews() {
        _isLoading.value = true
        repository.getNews().observeForever { result ->
            when (result) {
                is Result.Loading -> _isLoading.value = true // Show loading indicator
                is Result.Success -> { // Update news list with successful data
                    _isLoading.value = false
                    _listNews.value = result.data
                }
                is Result.Error -> { // Log errors and hide loading indicator
                    _isLoading.value = false
                    Log.e(TAG, "Error: ${result.error}")
                }
            }
        }
    }

    companion object {
        const val TAG = "NewsViewModel"
    }
}
