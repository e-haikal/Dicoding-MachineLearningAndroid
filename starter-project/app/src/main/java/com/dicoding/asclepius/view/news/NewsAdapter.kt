package com.dicoding.asclepius.view.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.remote.ArticlesItem
import com.dicoding.asclepius.databinding.ItemNewsBinding

// Adapter for displaying list of news articles in RecyclerView
class NewsAdapter(private val onItemClick: (String) -> Unit) : ListAdapter<ArticlesItem, NewsAdapter.NewsViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    // ViewHolder to bind each article's data to UI components in the item layout
    class NewsViewHolder(
        private val binding: ItemNewsBinding,
        private val onItemClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        // Binds news item data to views and sets click listener to open URL
        fun bind(news: ArticlesItem) {
            binding.tvNewsTitle.text = news.title
            binding.tvNewsDate.text = news.publishedAt
            binding.tvNewsDescription.text = news.description
            Glide.with(binding.ivNewsImage.context)
                .load(news.urlToImage)
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.ivNewsImage)

            // Trigger onItemClick with the news URL when item is clicked
            binding.root.setOnClickListener {
                onItemClick(news.url)
            }
        }
    }

    companion object {
        // Callback for efficient RecyclerView updates when data changes
        val DIFF_CALLBACK: DiffUtil.ItemCallback<ArticlesItem> =
            object : DiffUtil.ItemCallback<ArticlesItem>() {
                override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem) = oldItem.title == newItem.title
                override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem) = oldItem == newItem
            }
    }
}
