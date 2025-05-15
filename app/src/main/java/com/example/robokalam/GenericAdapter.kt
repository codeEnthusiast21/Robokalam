package com.example.robokalam

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.robokalam.databinding.ItemCardBinding
import com.example.robokalam.databinding.ItemPurchasedCourseBinding

// GenericAdapter.kt
class GenericAdapter : ListAdapter<ItemModel, GenericAdapter.ViewHolder>(DIFF_CALLBACK) {

    class ViewHolder(private val binding: ItemPurchasedCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ItemModel) {
            binding.apply {
                tvTitle.text = item.title
                tvProgress.text = item.progress
                ivCourseImage.setImageResource(item.imageResId)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val binding = ItemPurchasedCourseBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemModel>() {
            override fun areItemsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}
