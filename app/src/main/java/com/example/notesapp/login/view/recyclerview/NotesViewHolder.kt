package com.example.notesapp.login.view.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.login.data.Notes
import com.example.notesapp.login.db.NotesRow
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.textview.MaterialTextView

class NotesViewHolder(itemView: View, private val itemClickListener: NotesClickListener) : RecyclerView.ViewHolder(itemView) {
    fun bind(notes: Notes) {
        itemView.findViewById<MaterialTextView>(R.id.tvTitle).apply {
            text = notes.title
        }
        itemView.findViewById<MaterialTextView>(R.id.tvDescription).apply {
            text = notes.description
        }
        itemView.findViewById<MaterialCheckBox>(R.id.taskCheckbox).apply {

            this.isChecked = notes.isTaskDone
            this.setOnCheckedChangeListener { _, isChecked ->
                itemClickListener.onTaskChecked(notesRow = notes.getUpdated(isChecked))
            }
        }
    }
}

private fun Notes.getUpdated(checked: Boolean): NotesRow {
    return NotesRow(isTaskDone = checked, title = this.title, description = this.description, id = this.id)
}
