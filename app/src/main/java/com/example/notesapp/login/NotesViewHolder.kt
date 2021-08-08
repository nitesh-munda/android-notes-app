package com.example.notesapp.login

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.login.data.Notes
import com.google.android.material.textview.MaterialTextView

class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(notes: Notes) {
        itemView.findViewById<MaterialTextView>(R.id.tvTitle).apply {
            text = notes.title
        }
        itemView.findViewById<MaterialTextView>(R.id.tvDescription).apply {
            text = notes.description
        }
    }
}