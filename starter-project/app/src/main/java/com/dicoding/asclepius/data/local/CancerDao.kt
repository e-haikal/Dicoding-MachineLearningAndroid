package com.dicoding.asclepius.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

// Data Access Object (DAO) for accessing the Cancer database table
@Dao
interface CancerDao {
    // Fetches all records from the cancers_history table as LiveData
    @Query("SELECT * FROM cancers_history")
    fun getCancers(): LiveData<List<CancerEntity>>

    // Inserts a list of CancerEntity records; ignores conflicts if record exists
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCancer(cancer: List<CancerEntity>)
}
