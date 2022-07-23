package com.darkshandev.restohunt.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.darkshandev.restohunt.core.domain.models.Restaurant
import com.darkshandev.restohunt.databinding.ItemLayoutBinding

class ListViewAdapter(private val onItemClicked: (String) -> Unit) :
    ListAdapter<Restaurant, ListViewAdapter.ItemViewHolder>(DIFF_CALLBACK) {

    inner class ItemViewHolder(val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Restaurant>() {
            override fun areItemsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
                return oldItem == newItem
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(
            ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        getItem(position)?.let { restaurant ->
            holder.binding.restaurant = restaurant
            holder.binding.root.setOnClickListener {
                onItemClicked(restaurant.id)
            }
        }
    }


}