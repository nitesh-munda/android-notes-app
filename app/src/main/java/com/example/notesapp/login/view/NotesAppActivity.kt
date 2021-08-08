package com.example.notesapp.login.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.databinding.ActivityMainBinding
import com.example.notesapp.login.NotesApplication
import com.example.notesapp.login.view.recyclerview.RVAdapter
import com.example.notesapp.login.appConstants.AppConstants.Companion.FULL_NAME
import com.example.notesapp.login.data.Notes
import com.example.notesapp.login.db.NotesRow
import com.example.notesapp.login.view.recyclerview.NotesClickListener
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlin.random.Random

class NotesAppActivity : AppCompatActivity(), NotesClickListener {

    lateinit var binding : ActivityMainBinding

    private val rvAdapter = RVAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val list = fetchFromDatabase()
        setupRecyclerView(list)
        setupActionBar()
        setupFab()
    }

    private fun fetchFromDatabase() : List<NotesRow>{
        (applicationContext as NotesApplication).getDatabase().apply {
            val dao = this.getNotesRowDao()
            return dao.getAllNotes()
        }
    }

    private fun setupRecyclerView(list: List<NotesRow>) {
        binding.rvList.apply {
            layoutManager = LinearLayoutManager(this@NotesAppActivity, RecyclerView.VERTICAL, false)
            adapter = rvAdapter
        }

        if (list.isNotEmpty()) {
            val rvList = mutableListOf<Notes>()
            list.forEach {
                rvList.add(it.convertToRVItem())
            }
            rvAdapter.initAdapter(rvList)
        }
    }

    private fun setupFab() {
        binding.fabButton.setOnClickListener {
            setupDialog()
        }
    }

    private fun setupDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_notes, null)
        val editTextHeading = dialogView.findViewById<TextInputEditText>(R.id.et_heading)
        val editTextDescription = dialogView.findViewById<TextInputEditText>(R.id.et_description)
        val button = dialogView.findViewById<MaterialButton>(R.id.buttonLayout)
        val dialog = AlertDialog.Builder(this).setView(dialogView).setCancelable(true).create()
        dialog?.show()

        button.setOnClickListener {
            val notes = Notes(
                id = System.currentTimeMillis().toInt(),
                title = editTextHeading.text.toString(),
                description = editTextDescription.text.toString(),
                isTaskDone = false
            )
            rvAdapter.updateData(notes)
            addNotesToDB(notes)
            dialog?.hide()
        }
    }

    private fun addNotesToDB(notes: Notes) {
        (applicationContext as NotesApplication).apply {
            val dao = getDatabase().getNotesRowDao()
            dao.insertNotes(notes.convertToDBRow())
        }
    }

    private fun setupActionBar() {
        supportActionBar?.title = intent.extras?.get(FULL_NAME) as? String ?: "MyNotesApp"
    }

    override fun onTaskChecked(notesRow: NotesRow) {
        (applicationContext as NotesApplication).apply {
            val dao = getDatabase().getNotesRowDao()
            dao.updateNotes(notesRow)
        }
    }
}

private fun Notes.convertToDBRow(): NotesRow {
    return NotesRow(title = title, description = description, isTaskDone = isTaskDone, id = id)
}

private fun NotesRow.convertToRVItem() : Notes{
    return Notes(id = id, title = title, description = description, isTaskDone = isTaskDone)
}
