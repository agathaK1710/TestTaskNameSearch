package com.example.testtasknamesearch.presentation.favotitesPage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testtasknamesearch.databinding.NameItemBinding


class FavouritesAdapter(
    private val favNames: List<String>
) : RecyclerView.Adapter<FavoriteViewHolder>() {
    var onLongClickListener: ((String) -> Unit)? = null
    var onCheckBoxClickListener: ((String) -> Unit)? = null
    var isLongPressed = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = NameItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val currentName = favNames[position]
        with(holder) {
            if (isLongPressed) {
                checkBox.visibility =
                    if (checkBox.visibility == View.GONE) View.VISIBLE else View.GONE
            } else {
                checkBox.visibility = View.GONE
            }
            name.text = currentName
            itemView.setOnLongClickListener {
                showCheckbox()
                onLongClickListener?.invoke(currentName)
                true
            }
            checkBox.setOnClickListener {
                onCheckBoxClickListener?.invoke(currentName)
            }
        }
    }

    private fun showCheckbox() {
        isLongPressed = true
        notifyDataSetChanged()
    }

    override fun getItemCount() = favNames.size
}