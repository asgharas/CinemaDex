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
import com.asgharas.cinemadex.model.data.FavMovie
import com.asgharas.cinemadex.utils.IMAGE_BASE_URL
import com.bumptech.glide.Glide

class MovieAdapter(
    private val context: Context,
    private val handleLongClick: (Parcelable) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(val binding: MovieViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: FavMovie) {
            Glide.with(context)
                .load(IMAGE_BASE_URL + movie.poster_path)
                .placeholder(R.drawable.template_placeholder)
                .error(R.drawable.template_placeholder)
                .override(500, 750)
                .into(binding.rvMoviePoster)
        }
    }

    private val diffUtil = object : DiffUtil.ItemCallback<FavMovie>() {
        override fun areItemsTheSame(oldItem: FavMovie, newItem: FavMovie): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: FavMovie, newItem: FavMovie): Boolean =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            MovieViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.binding.rvMoviePoster.setOnLongClickListener {
            handleLongClick(differ.currentList[position])
            return@setOnLongClickListener true
        }
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int =
        differ.currentList.size
}