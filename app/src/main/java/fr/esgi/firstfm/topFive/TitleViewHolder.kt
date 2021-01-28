package fr.esgi.firstfm.topFive

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.esgi.firstfm.R

class TitleViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.title_item, parent, false)) {
    private var titleTextView: TextView? = null

    init {
        titleTextView = itemView.findViewById(R.id.titleTextView)
    }

    fun bind(title: String) {
        titleTextView?.text = title
    }
}