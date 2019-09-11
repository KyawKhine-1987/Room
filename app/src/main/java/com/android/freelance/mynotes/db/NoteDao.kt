package com.android.freelance.mynotes.db

import androidx.room.*

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note: Note)

    @Query("select * from note nolock order by id desc;")
    suspend fun getAllNotes(): List<Note>

    @Insert
    suspend fun addMultipleNotes(vararg note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Query("update note set title = :head, note = :body where id = :mId;")
    suspend fun modifyNote(head : String, body: String, mId : Int)

    @Delete
    suspend fun deleteNote(note: Note)
}