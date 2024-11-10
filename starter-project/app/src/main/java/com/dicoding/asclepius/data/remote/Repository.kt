package com.dicoding.asclepius.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData

// Repository to manage data operations from ApiService
class Repository private constructor(
    private val apiService: ApiService  // API service for network requests
) {
    // Fetches news articles and exposes result as LiveData
    fun getNews(): LiveData<Result<List<ArticlesItem>>> = liveData {
        emit(Result.Loading)  // Emits loading state
        try {
            // Calls the API to fetch news articles matching query, category, language, and API key
            val response = apiService.getNews(
                query = "cancer",
                category = "health",
                language = "en",
                apiKey = "cb6c561f7c08482eada7612d2a923ef3"
            )
            emit(Result.Success(response.articles))  // Emits success with the articles list
        } catch (e: Exception) {
            // Logs and emits error if API call fails
            Log.d(TAG, "Repository error : ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object {
        const val TAG = "Repository"

        // Ensures a single instance of Repository (Singleton pattern)
        @Volatile
        private var instance: Repository? = null

        fun getInstance(apiService: ApiService): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(apiService)
            }.also { instance = it }
    }
}
