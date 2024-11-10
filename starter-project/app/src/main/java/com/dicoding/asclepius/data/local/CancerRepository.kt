package com.dicoding.asclepius.data.local

import androidx.lifecycle.LiveData

// Repository to handle data operations for CancerEntity
class CancerRepository private constructor(
    private val cancerDao: CancerDao  // DAO for database interactions
) {
    // Retrieves all cancer history records as LiveData
    fun getCancers(): LiveData<List<CancerEntity>> = cancerDao.getCancers()

    // Inserts a list of cancer history records into the database
    suspend fun insertCancers(cancers: List<CancerEntity>) {
        cancerDao.insertCancer(cancers)
    }

    companion object {
        @Volatile
        private var instance: CancerRepository? = null

        // Singleton instance to avoid creating multiple instances of CancerRepository
        fun getInstance(cancerDao: CancerDao): CancerRepository =
            instance ?: synchronized(this) {
                instance ?: CancerRepository(cancerDao)
            }.also { instance = it }
    }
}
