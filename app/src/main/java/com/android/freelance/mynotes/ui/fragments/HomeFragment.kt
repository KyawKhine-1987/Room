package com.android.freelance.mynotes.ui.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager

import com.android.freelance.mynotes.R
import com.android.freelance.mynotes.db.NoteDatabase
import com.android.freelance.mynotes.ui.adapter.NotesAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : BaseFragment() {

    private val LOG_TAG = HomeFragment::class.java.name

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.i(LOG_TAG, "TEST : onCreateView() is called...")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.i(LOG_TAG, "TEST : onActivityCreated() is called...")

        super.onActivityCreated(savedInstanceState)
        rvNotesList.setHasFixedSize(true)
        rvNotesList.layoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        launch {
            context?.let {
                val notes = NoteDatabase(it).noteDao().getAllNotes()
                rvNotesList.adapter = NotesAdapter(notes)
            }
        }

        fabAdd.setOnClickListener {
            // going ahead to AddNoteFragment
            val action = HomeFragmentDirections.actionAddNote()//need to build below this code
            Navigation.findNavController(it).navigate(action)
        }

    }
}
