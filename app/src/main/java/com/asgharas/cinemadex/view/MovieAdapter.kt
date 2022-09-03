package com.asgharas.cinemadex.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.asgharas.cinemadex.R
import com.asgharas.cinemadex.databinding.MovieViewBinding
import com.asgharas.cinemadex.model.data.Movie
import com.asgharas.cinemadex.other.IMAGE_BASE_URL
import com.bumptech.glide.Glide

class MovieAdapter(
    private val context: Context,
    private val movieClickListener: MovieClickListener,
    private val reachedBottomListener: ReachedBottomListener
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

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

    private val diffUtil = object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
            oldItem == newItem
    }

    val differ = AsyncListDiffer<Movie>(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            MovieViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.binding.rvMoviePoster.setOnClickListener {
            movieClickListener.handleMovieClick(differ.currentList[position])
        }
        holder.bind(differ.currentList[position])

        if(position == differ.currentList.size - 1){
            reachedBottomListener.onReachedBottom()
        }
    }

    override fun getItemCount(): Int =
        differ.currentList.size
}