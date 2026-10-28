package com.mmgpt.shoplistgpt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mmgpt.shoplistgpt.CategoryTitle
import com.mmgpt.shoplistgpt.R
import com.mmgpt.shoplistgpt.ShoppingCategory
import com.mmgpt.shoplistgpt.databinding.ItemCategorySummaryBinding

class CategorySummaryAdapter : ListAdapter<ShoppingCategory, CategorySummaryAdapter.ViewHolder>(DIFF_CALLBACK) {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategorySummaryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemId(position: Int): Long = getItem(position).id

    class ViewHolder(private val binding: ItemCategorySummaryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: ShoppingCategory) {
            val title = CategoryTitle.parse(category.heading)
            binding.tvNumber.text = title.number
            binding.tvName.text = title.name
            val context = binding.root.context
            binding.tvCount.text = context.getString(
                R.string.count_of_total, category.remainingCount, category.totalCount
            )
            binding.tvFlagged.text = category.flaggedCount.toString()
            // Flags only draw the eye when there is something flagged
            binding.tvFlagged.alpha = if (category.flaggedCount > 0) 1f else 0.35f
            binding.progress.max = maxOf(category.totalCount, 1)
            binding.progress.progress = category.remainingCount
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ShoppingCategory>() {
            override fun areItemsTheSame(oldItem: ShoppingCategory, newItem: ShoppingCategory) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: ShoppingCategory, newItem: ShoppingCategory) = oldItem == newItem
        }
    }
}
