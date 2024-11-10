package com.dicoding.asclepius.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Configures and provides the Retrofit instance for making network requests
class ApiConfig {
    companion object {
        fun getApiService(): ApiService {
            // Creates a logging interceptor to log HTTP request and response details
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

            // Builds OkHttpClient with the logging interceptor for detailed request logging
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            // Configures and creates the Retrofit instance with base URL and Gson converter
            val retrofit = Retrofit.Builder()
                .baseUrl("https://newsapi.org/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            // Returns an instance of ApiService using the configured Retrofit instance
            return retrofit.create(ApiService::class.java)
        }
    }
}
