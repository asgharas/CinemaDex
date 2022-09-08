package com.asgharas.cinemadex.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.asgharas.cinemadex.R
import com.asgharas.cinemadex.databinding.ActivitySingleMovieBinding
import com.asgharas.cinemadex.model.data.Movie
import com.asgharas.cinemadex.other.IMAGE_BASE_URL
import com.asgharas.cinemadex.viewmodel.FavouriteViewModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingleMovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingleMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val favouriteViewModel = ViewModelProvider(this)[FavouriteViewModel::class.java]
        val movie: Movie? = intent.getParcelableExtra("movie_extra")
        if(movie != null) {
            setupUi(movie)
            binding.btnFavourite.setOnClickListener {
                favouriteViewModel.addMovieFavourite(movie)
                Snackbar.make(binding.root, "Added to favourites", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupUi(movie: Movie) {
        Glide.with(this)
            .load(IMAGE_BASE_URL +movie.poster_path)
            .placeholder(R.drawable.template_placeholder)
            .error(R.drawable.template_placeholder)
            .into(binding.ivPoster)

        binding.tvTitle.text = movie.title
        binding.tvStars.text = movie.vote_average.toString()
        binding.tvVotes.text = movie.vote_count.toString()
        binding.tvDescription.text = movie.overview
    }
}