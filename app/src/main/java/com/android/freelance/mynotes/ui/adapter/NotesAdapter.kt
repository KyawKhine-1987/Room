package com.android.freelance.mynotes.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.android.freelance.mynotes.R
import com.android.freelance.mynotes.db.Note
import com.android.freelance.mynotes.ui.fragments.HomeFragmentDirections
import kotlinx.android.synthetic.main.item_row_note.view.*

class NotesAdapter(val notes: List<Note>) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val LOG_TAG = NotesAdapter::class.java.name

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        Log.i(LOG_TAG, "TEST : onBindViewHolder() is called...")

        holder.view.tvTitle.text = notes.get(position).title
        holder.view.tvNote.text = notes.get(position).note

        //select and tap the note on Recycler View
        holder.view.setOnClickListener {
            val action = HomeFragmentDirections.actionAddNote()
            action.note = notes[position]
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        Log.i(LOG_TAG, "TEST : onCreateViewHolder() is called...")

        return NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_note, parent, false)
        )
    }

    override fun getItemCount(): Int = notes.size

    class NoteViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}