package com.asgharas.cinemadex.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.asgharas.cinemadex.R
import com.asgharas.cinemadex.databinding.SearchItemBinding
import com.asgharas.cinemadex.model.data.Movie
import com.asgharas.cinemadex.utils.IMAGE_BASE_URL
import com.bumptech.glide.Glide

class MovieSearchAdapter(
    private val context: Context,
    private val handleMovieClick: (Movie) -> Unit
) : RecyclerView.Adapter<MovieSearchAdapter.MovieSearchViewHolder>() {

    inner class MovieSearchViewHolder(val binding: SearchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.itemName.text = movie.title
            Glide.with(context)
                .load(IMAGE_BASE_URL + movie.poster_path)
                .placeholder(R.drawable.template_placeholder)
                .error(R.drawable.template_placeholder)
                .into(binding.itemPoster)
        }
    }

    private val diffUtil = object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer<Movie>(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieSearchViewHolder {
        val binding = SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieSearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieSearchViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
        holder.binding.root.setOnClickListener {
            handleMovieClick(differ.currentList[position])
        }
    }

    override fun getItemCount(): Int =
        differ.currentList.size
}