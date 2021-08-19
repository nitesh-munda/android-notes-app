package com.example.notesapp.login.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.notesapp.databinding.DialogNotesBinding
import com.example.notesapp.login.appConstants.AppConstants.Companion.NOTES_DESC
import com.example.notesapp.login.appConstants.AppConstants.Companion.NOTES_TITLE

class NotesDetailsActivity : AppCompatActivity() {

    lateinit var binding: DialogNotesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {

        binding.buttonLayout.setOnClickListener {
            val intent = Intent().apply {
                putExtra(NOTES_TITLE, binding.etHeading.text.toString())
                putExtra(NOTES_DESC, binding.etDescription.text.toString())
            }

            setResult(RESULT_OK, intent)

            finish()
        }
    }
}