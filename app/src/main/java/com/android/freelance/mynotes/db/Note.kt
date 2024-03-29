package com.android.freelance.mynotes.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.sql.Blob

//(tableName = "note")
@Entity
data class Note(

    val title: String,
    val note: String
): Serializable{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}