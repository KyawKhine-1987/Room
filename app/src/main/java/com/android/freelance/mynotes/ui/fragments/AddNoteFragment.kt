package com.android.freelance.mynotes.ui.fragments


import android.app.AlertDialog
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.navigation.Navigation

import com.android.freelance.mynotes.R
import com.android.freelance.mynotes.db.Note
import com.android.freelance.mynotes.db.NoteDatabase
import com.android.freelance.mynotes.ui.util.toast
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class AddNoteFragment : BaseFragment() {

    private val LOG_TAG = AddNoteFragment::class.java.name
    private var note: Note? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.i(LOG_TAG, "TEST : onCreateView() is called...")
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.i(LOG_TAG, "TEST : onActivityCreated() is called...")

        super.onActivityCreated(savedInstanceState)

        //update
        arguments?.let {
            note = AddNoteFragmentArgs.fromBundle(it).note
            etTitle.setText(note?.title)
            etNote.setText(note?.note)
        }

        fabSave.setOnClickListener { view ->
            val title = etTitle.text.toString().trim()
            val noteBody = etNote.text.toString().trim()

            if (title.isEmpty()) {
                etTitle.error = "Title is required!"
                etTitle.requestFocus()
                return@setOnClickListener
            }

            if (noteBody.isEmpty()) {
                etNote.error = "Note is required!"
                etNote.requestFocus()
                return@setOnClickListener
            }

            //TODO reference for New Wonders
            launch {
                context?.let {
                    val mNote = Note(title, noteBody)

                    if (note == null) {

                        NoteDatabase(it).noteDao().addNote(mNote)
                        it.toast("Note Saved.")
                    } else {
                        mNote.id = note!!.id
                       /* NoteDatabase(it).noteDao().updateNote(mNote)*/
                        NoteDatabase(it).noteDao().modifyNote(mNote.title, mNote.note,mNote.id)
                        it.toast("Note Updated.")
                    }

                    goBackHomeFragment()
                }
            }

        }
    }

    private fun goBackHomeFragment() {
        Log.i(LOG_TAG, "TEST : goBackHomeFragment() is called...")

        // going back to HomeFragment
        val action =
            AddNoteFragmentDirections.actionSaveNote()//need to build below this code
        Navigation.findNavController(view!!).navigate(action)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.i(LOG_TAG, "TEST : onOptionsItemSelected() is called...")

        when (item.itemId) {
            R.id.delete -> if (note != null) {
                deleteNote()
            } else {
                context?.toast("Can't Delete!")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteNote() {
        Log.i(LOG_TAG, "TEST : deleteNote() is called...")

        AlertDialog.Builder(context).apply {
            setTitle("Are you sure?")
            setMessage("You can't undo this operation.")
            setPositiveButton("Yes") { _, _ ->

                launch {
                    NoteDatabase(context).noteDao().deleteNote(note!!)
                    goBackHomeFragment()
                }
            }
            setNegativeButton("No") { _, _ ->

            }
        }.create().show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        Log.i(LOG_TAG, "TEST : onCreateOptionsMenu() is called...")

        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    //Solve this error "Cannot access database on the main thread since it may potentially lock the UI for a long period of time."
    /*private fun saveNote(note: Note) {
        class PopulateSaveNote : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg p0: Void?): Void? {
                Log.i(LOG_TAG, "TEST : doInBackground() is called...")

                NoteDatabase(activity!!).noteDao().addNote(note)
                return null
            }

            override fun onPostExecute(result: Void?) {
                Log.i(LOG_TAG, "TEST : onPostExecute() is called...")

                super.onPostExecute(result)
                Toast.makeText(context, "Not Saved!", Toast.LENGTH_LONG).show()
            }
        }
        PopulateSaveNote().execute()
    }*/
}
