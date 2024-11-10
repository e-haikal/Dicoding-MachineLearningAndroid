package com.dicoding.asclepius.view.history

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.asclepius.data.local.CancerRepository
import com.dicoding.asclepius.di.Injection

// Factory to create HistoryViewModel with a specific repository
class HistoryFactory private constructor(private val cancerRepository: CancerRepository): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Returns HistoryViewModel instance if requested
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            return HistoryViewModel(cancerRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: HistoryFactory? = null

        // Singleton pattern to ensure a single instance of HistoryFactory
        fun getInstance(context: Context): HistoryFactory =
            instance ?: synchronized(this) {
                instance ?: HistoryFactory(Injection.provideCancerRepository(context))
            }.also { instance = it }
    }
}
