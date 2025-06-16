package com.example.myassssmentapplication.ui.dashboard

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myassssmentapplication.data.model.Entity
import com.example.myassssmentapplication.databinding.ItemEntityBinding

class EntityAdapter(
    private val onItemClick: (Entity) -> Unit
) : ListAdapter<Entity, EntityAdapter.EntityViewHolder>(EntityDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntityViewHolder {
        val binding = ItemEntityBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EntityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EntityViewHolder, position: Int) {
        try {
            val item = getItem(position)
            holder.bind(item)
        } catch (e: Exception) {
            Log.e("EntityAdapter", "Error binding item at position $position", e)
        }
    }

    inner class EntityViewHolder(
        private val binding: ItemEntityBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                try {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val entity = getItem(position)
                        Log.d("EntityAdapter", "Item clicked: $entity")
                        onItemClick(entity)
                    }
                } catch (e: Exception) {
                    Log.e("EntityAdapter", "Error handling item click", e)
                }
            }
        }

        fun bind(entity: Entity) {
            try {
                binding.fieldsContainer.removeAllViews()
                // Show only the 'name' field if present, otherwise the first key
                val displayText = when {
                    entity.containsKey("name") -> entity["name"].toString()
                    entity.isNotEmpty() -> entity.entries.first().value.toString()
                    else -> "No name"
                }
                val textView = TextView(binding.root.context)
                textView.text = displayText
                textView.textSize = 16f
                binding.fieldsContainer.addView(textView)
            } catch (e: Exception) {
                Log.e("EntityAdapter", "Error binding entity data", e)
                binding.fieldsContainer.removeAllViews()
                val errorView = TextView(binding.root.context)
                errorView.text = "Error loading data"
                binding.fieldsContainer.addView(errorView)
            }
        }
    }

    private class EntityDiffCallback : DiffUtil.ItemCallback<Entity>() {
        override fun areItemsTheSame(oldItem: Entity, newItem: Entity): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Entity, newItem: Entity): Boolean {
            return oldItem == newItem
        }
    }
} 