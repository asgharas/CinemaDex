package com.asgharas.cinemadex.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.asgharas.cinemadex.R
import com.asgharas.cinemadex.databinding.MovieViewBinding
import com.asgharas.cinemadex.model.data.Tv
import com.asgharas.cinemadex.other.IMAGE_BASE_URL
import com.asgharas.cinemadex.view.listeners.LongClickListener
import com.asgharas.cinemadex.view.listeners.ReachedBottomListener
import com.asgharas.cinemadex.view.listeners.TvClickListener
import com.bumptech.glide.Glide

class TvAdapter(
    private val context: Context,
    private val tvClickListener: TvClickListener,
    private val reachedBottomListener: ReachedBottomListener,
    private val longClickListener: LongClickListener
) :
    RecyclerView.Adapter<TvAdapter.TvViewHolder>() {

    inner class TvViewHolder(val binding: MovieViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tv: Tv) {
            Glide.with(context)
                .load(IMAGE_BASE_URL + tv.poster_path)
                .placeholder(R.drawable.template_placeholder)
                .error(R.drawable.template_placeholder)
                .override(500, 750)
                .into(binding.rvMoviePoster)
        }
    }

    private val diffUtil = object : DiffUtil.ItemCallback<Tv>() {
        override fun areItemsTheSame(oldItem: Tv, newItem: Tv): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Tv, newItem: Tv): Boolean =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvViewHolder {
        val binding = MovieViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TvViewHolder, position: Int) {
        holder.binding.rvMoviePoster.setOnClickListener {
            tvClickListener.handleTvClick(differ.currentList[position])
        }
        holder.binding.rvMoviePoster.setOnLongClickListener {
            longClickListener.handleLongClick(differ.currentList[position])
            return@setOnLongClickListener true
        }
        holder.bind(differ.currentList[position])
        if(position == differ.currentList.size - 1) {
            reachedBottomListener.onReachedBottom()
        }
    }

    override fun getItemCount(): Int =
        differ.currentList.size
}