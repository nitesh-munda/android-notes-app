package com.example.notesapp.login.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.notesapp.R
import com.example.notesapp.databinding.DialogNotesBinding
import com.example.notesapp.databinding.ImageOptionsListBinding
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

        binding.ivNotes.setOnClickListener {
            val binding = ImageOptionsListBinding.inflate(layoutInflater)
            AlertDialog.Builder(this).setView(binding.root).setCancelable(true).create().show()
            binding.tvCamera.setOnClickListener {
                Log.d("NotesDetailsActivity","Nitesh$$$ - Camera clicked")
            }

            binding.tvaGallery.setOnClickListener {
                Log.d("NotesDetailsActivity","Nitesh$$$ - Gallery clicked")
            }
        }

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