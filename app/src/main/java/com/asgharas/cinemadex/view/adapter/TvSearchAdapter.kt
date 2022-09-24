package com.asgharas.cinemadex.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.asgharas.cinemadex.R
import com.asgharas.cinemadex.databinding.SearchItemBinding
import com.asgharas.cinemadex.model.data.Tv
import com.asgharas.cinemadex.utils.IMAGE_BASE_URL
import com.bumptech.glide.Glide

class TvSearchAdapter(
    private val context: Context,
    private val handleTvClick: (Tv) -> Unit
) : RecyclerView.Adapter<TvSearchAdapter.TvSearchViewHolder>() {

    inner class TvSearchViewHolder(val binding: SearchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tv: Tv) {
            binding.itemName.text = tv.name
            Glide.with(context)
                .load(IMAGE_BASE_URL + tv.poster_path)
                .placeholder(R.drawable.template_placeholder)
                .error(R.drawable.template_placeholder)
                .into(binding.itemPoster)
        }
    }

    private val diffUtil = object : DiffUtil.ItemCallback<Tv>() {
        override fun areItemsTheSame(oldItem: Tv, newItem: Tv): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Tv, newItem: Tv): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer<Tv>(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvSearchViewHolder {
        val binding = SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvSearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TvSearchViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
        holder.binding.root.setOnClickListener {
            handleTvClick(differ.currentList[position])
        }
    }

    override fun getItemCount(): Int =
        differ.currentList.size
}