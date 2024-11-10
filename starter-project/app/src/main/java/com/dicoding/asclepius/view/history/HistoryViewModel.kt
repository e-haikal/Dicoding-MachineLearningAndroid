package com.dicoding.asclepius.view.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.data.local.CancerEntity
import com.dicoding.asclepius.data.local.CancerRepository
import kotlinx.coroutines.launch

// ViewModel for managing history data
class HistoryViewModel(private val repository: CancerRepository): ViewModel() {
    // Holds live data of cancer history for UI updates
    val cancers: LiveData<List<CancerEntity>> = repository.getCancers()

    // Inserts cancer history data into the repository asynchronously
    fun insertCancers(cancers: List<CancerEntity>) = viewModelScope.launch {
        repository.insertCancers(cancers)
    }
}
