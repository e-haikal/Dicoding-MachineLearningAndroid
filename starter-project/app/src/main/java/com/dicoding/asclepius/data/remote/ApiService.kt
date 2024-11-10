package com.dicoding.asclepius.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

// Defines API endpoints and request parameters for Retrofit
interface ApiService {
    @GET("top-headlines")
    suspend fun getNews(
        @Query("q") query: String,           // Search query parameter
        @Query("category") category: String,  // News category (e.g., "health")
        @Query("language") language: String,  // Language of the news articles
        @Query("apiKey") apiKey: String       // API key for authentication
    ): Response                              // Returns a Response object
}
