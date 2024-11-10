package com.dicoding.asclepius.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Room Database class for managing the Cancer database
@Database(entities = [CancerEntity::class], version = 1, exportSchema = false)
abstract class CancerDatabase : RoomDatabase() {
    // Provides access to the CancerDao
    abstract fun cancerDao(): CancerDao

    companion object {
        @Volatile
        private var instance: CancerDatabase? = null

        // Singleton instance to avoid creating multiple database instances
        @JvmStatic
        fun getDatabase(context: Context): CancerDatabase {
            if (instance == null) {
                synchronized(CancerDatabase::class.java) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CancerDatabase::class.java, "cancer_database"
                    ).build()
                }
            }
            return instance as CancerDatabase
        }
    }
}
