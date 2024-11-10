package com.dicoding.asclepius.di

import android.content.Context
import com.dicoding.asclepius.data.local.CancerDatabase
import com.dicoding.asclepius.data.local.CancerRepository
import com.dicoding.asclepius.data.remote.ApiConfig
import com.dicoding.asclepius.data.remote.Repository

// Provides dependency injection for repositories
object Injection {
    // Creates and provides a singleton instance of the remote Repository
    fun provideRepository(context: Context): Repository {
        val apiService = ApiConfig.getApiService()  // Set up API service
        return Repository.getInstance(apiService)
    }

    // Creates and provides a singleton instance of the local CancerRepository
    fun provideCancerRepository(context: Context): CancerRepository {
        val database = CancerDatabase.getDatabase(context)  // Get local database instance
        val dao = database.cancerDao()  // Retrieve DAO from the database
        return CancerRepository.getInstance(dao)
    }
}
