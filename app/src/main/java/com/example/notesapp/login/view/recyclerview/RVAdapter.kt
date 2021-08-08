package com.example.notesapp.login.view.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.login.data.Notes

class RVAdapter(private val itemClickListener: NotesClickListener) : RecyclerView.Adapter<NotesViewHolder>() {

    private val data = mutableListOf<Notes>()

    fun updateData(data: Notes) {
        this.data.add(data)
        notifyItemInserted(this.data.size)
    }

    fun initAdapter(data: List<Notes>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_rv_item_list, parent, false)
        return NotesViewHolder(view, itemClickListener)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size


}