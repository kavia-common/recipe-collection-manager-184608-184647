package org.example.app.ui.widgets

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.example.app.R
import org.example.app.data.local.FavoriteRecipe
import org.example.app.ui.common.loadUrlOrPlaceholder

class FavoriteAdapter(
    private val onClick: (FavoriteRecipe) -> Unit,
    private val onRemove: (FavoriteRecipe) -> Unit
) : ListAdapter<FavoriteRecipe, FavoriteAdapter.VH>(DIFF) {

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<FavoriteRecipe>() {
            override fun areItemsTheSame(oldItem: FavoriteRecipe, newItem: FavoriteRecipe) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: FavoriteRecipe, newItem: FavoriteRecipe) = oldItem == newItem
        }
    }

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val image: ImageView = view.findViewById(R.id.image)
        val remove: ImageButton = view.findViewById(R.id.remove_btn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite_card, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        holder.title.text = item.title
        holder.image.loadUrlOrPlaceholder(item.imageUrl)
        holder.itemView.setOnClickListener { onClick(item) }
        holder.remove.setOnClickListener { onRemove(item) }
    }
}
