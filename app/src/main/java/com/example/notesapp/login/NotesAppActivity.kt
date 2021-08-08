package com.example.notesapp.login

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.databinding.ActivityMainBinding
import com.example.notesapp.login.appConstants.AppConstants.Companion.FULL_NAME
import com.example.notesapp.login.data.Notes
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class NotesAppActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    private val rvAdapter = RVAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupActionBar()
        setupFab()
    }

    private fun setupRecyclerView() {
        binding.rvList.apply {
            layoutManager = LinearLayoutManager(this@NotesAppActivity, RecyclerView.VERTICAL, false)
            adapter = rvAdapter
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
                title = editTextHeading.text.toString(),
                description = editTextDescription.text.toString()
            )
            rvAdapter.updateData(notes)
            dialog?.hide()
        }
    }

    private fun setupActionBar() {
        supportActionBar?.title = intent.extras?.get(FULL_NAME) as? String ?: "MyNotesApp"
    }
}