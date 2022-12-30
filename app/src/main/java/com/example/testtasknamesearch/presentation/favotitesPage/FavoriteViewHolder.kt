package com.example.testtasknamesearch.presentation.favotitesPage

import androidx.recyclerview.widget.RecyclerView
import com.example.testtasknamesearch.databinding.NameItemBinding

class FavoriteViewHolder(itemView: NameItemBinding): RecyclerView.ViewHolder(itemView.root) {
    val name = itemView.tvName
    val checkBox = itemView.checkbox
}