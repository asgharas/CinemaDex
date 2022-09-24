package com.asgharas.cinemadex.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.asgharas.cinemadex.R
import com.asgharas.cinemadex.databinding.MovieViewBinding
import com.asgharas.cinemadex.model.data.Movie
import com.asgharas.cinemadex.utils.IMAGE_BASE_URL
import com.bumptech.glide.Glide

class MoviePagingAdapter(
    private val context: Context,
    private val handleMovieClick: (Movie) -> Unit
) :
    PagingDataAdapter<Movie, MoviePagingAdapter.MovieViewHolder>(
        COMPARATOR
    ) {

    inner class MovieViewHolder(val binding: MovieViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            Glide.with(context)
                .load(IMAGE_BASE_URL + movie.poster_path)
                .placeholder(R.drawable.template_placeholder)
                .error(R.drawable.template_placeholder)
                .override(500, 750)
                .into(binding.rvMoviePoster)
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            MovieViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.binding.rvMoviePoster.setOnClickListener {
            val item = getItem(position)
            if(item != null){
                handleMovieClick(item)
            }
        }

        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }
}