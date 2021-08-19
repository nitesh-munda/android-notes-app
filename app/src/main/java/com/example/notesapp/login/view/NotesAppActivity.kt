package com.example.notesapp.login.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.databinding.ActivityMainBinding
import com.example.notesapp.login.NotesApplication
import com.example.notesapp.login.view.recyclerview.RVAdapter
import com.example.notesapp.login.appConstants.AppConstants.Companion.FULL_NAME
import com.example.notesapp.login.appConstants.AppConstants.Companion.NOTES_DESC
import com.example.notesapp.login.appConstants.AppConstants.Companion.NOTES_TITLE
import com.example.notesapp.login.data.Notes
import com.example.notesapp.login.db.NotesRow
import com.example.notesapp.login.view.recyclerview.NotesClickListener
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlin.random.Random

class NotesAppActivity : AppCompatActivity(), NotesClickListener {

    lateinit var binding : ActivityMainBinding

    private val rvAdapter = RVAdapter(this)

    companion object {
        const val OPEN_DETAILS_PAGE = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val list = fetchFromDatabase()
        setupRecyclerView(list)
        setupActionBar()
        setupFab()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            OPEN_DETAILS_PAGE -> {
                if (resultCode == RESULT_OK) {
                    val title = data?.extras?.get(NOTES_TITLE) as? String ?: ""
                    val desc = data?.extras?.get(NOTES_DESC) as? String ?: ""
                    val notes = Notes(
                        id = System.currentTimeMillis().toInt(),
                        title = title,
                        description = desc,
                        isTaskDone = false
                    )
                    rvAdapter.updateData(notes)
                    addNotesToDB(notes)
                }
            }
        }

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
            openNotesDetailsPage()
        }
    }

    private fun openNotesDetailsPage() {
        val intent = Intent(this, NotesDetailsActivity::class.java)
        ActivityCompat.startActivityForResult(this, intent, OPEN_DETAILS_PAGE, null)
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
