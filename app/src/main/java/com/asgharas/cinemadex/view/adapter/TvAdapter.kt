package com.asgharas.cinemadex.view.adapter

import android.content.Context
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.asgharas.cinemadex.R
import com.asgharas.cinemadex.databinding.MovieViewBinding
import com.asgharas.cinemadex.model.data.FavTv
import com.asgharas.cinemadex.utils.IMAGE_BASE_URL
import com.bumptech.glide.Glide

class TvAdapter(
    private val context: Context,
    private val handleLongClick: (Parcelable) -> Unit
) :
    RecyclerView.Adapter<TvAdapter.TvViewHolder>() {

    inner class TvViewHolder(val binding: MovieViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tv: FavTv) {
            Glide.with(context)
                .load(IMAGE_BASE_URL + tv.poster_path)
                .placeholder(R.drawable.template_placeholder)
                .error(R.drawable.template_placeholder)
                .override(500, 750)
                .into(binding.rvMoviePoster)
        }
    }

    private val diffUtil = object : DiffUtil.ItemCallback<FavTv>() {
        override fun areItemsTheSame(oldItem: FavTv, newItem: FavTv): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: FavTv, newItem: FavTv): Boolean =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvViewHolder {
        val binding = MovieViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TvViewHolder, position: Int) {
        holder.binding.rvMoviePoster.setOnLongClickListener {
            handleLongClick(differ.currentList[position])
            return@setOnLongClickListener true
        }
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int =
        differ.currentList.size
}