package com.dicoding.asclepius.data.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

// Entity representing a cancer history record in the database
@Entity(tableName = "cancers_history")
@Parcelize
data class CancerEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,                 // Unique ID for each cancer history record

    @ColumnInfo(name = "image")
    var image: String,                // Path or URL to the image related to the record

    @ColumnInfo(name = "result")
    var result: String                // Result or diagnosis related to the cancer history record
) : Parcelable                       // Implements Parcelable for easy data passing between components
