package com.example.notesapp.login.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.example.notesapp.R
import com.example.notesapp.databinding.DialogNotesBinding
import com.example.notesapp.databinding.ImageOptionsListBinding
import com.example.notesapp.login.appConstants.AppConstants.Companion.NOTES_DESC
import com.example.notesapp.login.appConstants.AppConstants.Companion.NOTES_TITLE
import java.io.File
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class NotesDetailsActivity : AppCompatActivity() {

    lateinit var binding: DialogNotesBinding

    private var photoFile: File? = null

    private var currentPhotoPath: String? = null

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            REQUEST_IMAGE_CAPTURE -> {
                val uri = data?.extras?.get(MediaStore.EXTRA_OUTPUT)
                if (uri!=null){
                    Glide.with(this@NotesDetailsActivity).load(uri).into(binding.ivNotes)
                }
            }
        }
    }

    private fun setupViews() {

        binding.ivNotes.setOnClickListener {
            val binding = ImageOptionsListBinding.inflate(layoutInflater)
            AlertDialog.Builder(this).setView(binding.root).setCancelable(true).create().show()
            binding.tvCamera.setOnClickListener {
                Log.d("NotesDetailsActivity","Nitesh$$$ - Camera clicked")
                dispatchTakePictureIntent()
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

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { dispatchCameraIntent ->
            dispatchCameraIntent.resolveActivity(packageManager)?.also {
                photoFile  = try {
                    createTempFileForCamera()
                } catch (e: Exception) {
                    Log.e("NoteDetailsActivity", e.toString())
                    null
                }

                photoFile?.also {
                    val photoUri : Uri = FileProvider.getUriForFile(
                        this,
                        "com.example.android.fileprovider",
                        it
                    )
                    dispatchCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                    ActivityCompat.startActivityForResult(this, dispatchCameraIntent, REQUEST_IMAGE_CAPTURE, null)
                }
            }
        }
    }

    private fun createTempFileForCamera(): File? {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        storageDir?.let {
            return File.createTempFile(
                "JPEG_${timeStamp}_", /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
            ).apply {
                // Save a file: path for use with ACTION_VIEW intents
                currentPhotoPath = absolutePath
            }
        } ?: throw Exception("File not created")
    }
}