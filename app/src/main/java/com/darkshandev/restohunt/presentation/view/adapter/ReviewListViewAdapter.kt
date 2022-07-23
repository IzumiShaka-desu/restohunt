package com.darkshandev.restohunt.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.darkshandev.restohunt.core.domain.models.CustomerReview
import com.darkshandev.restohunt.databinding.ReviewItemBinding

class ReviewListViewAdapter :
    ListAdapter<CustomerReview, ReviewListViewAdapter.ItemViewHolder>(DIFF_CALLBACK) {

    inner class ItemViewHolder(val binding: ReviewItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CustomerReview>() {
            override fun areItemsTheSame(
                oldItem: CustomerReview,
                newItem: CustomerReview
            ): Boolean {
                return oldItem.review == newItem.review
            }

            override fun areContentsTheSame(
                oldItem: CustomerReview,
                newItem: CustomerReview
            ): Boolean {
                return oldItem == newItem
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(
            ReviewItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        getItem(position)?.let { customerReview ->
            holder.binding.review = customerReview
        }
    }


}