package com.asgharas.cinemadex.view.activity

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.asgharas.cinemadex.R
import com.asgharas.cinemadex.databinding.ActivitySingleTvBinding
import com.asgharas.cinemadex.model.data.Tv
import com.asgharas.cinemadex.utils.IMAGE_BASE_URL
import com.asgharas.cinemadex.viewmodel.FavouriteViewModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingleTvActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingleTvBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleTvBinding.inflate(layoutInflater)
        setContentView(binding.root)
        @Suppress("DEPRECATION") val tvShow = if(Build.VERSION.SDK_INT >= 33){
            intent.getParcelableExtra("tv_show", Tv::class.java)
        } else {
            intent.getParcelableExtra("tv_show")
        }
        val favouriteViewModel = ViewModelProvider(this)[FavouriteViewModel::class.java]
        if(tvShow != null) {
            setupUi(tvShow = tvShow)
            binding.btnFavourite.setOnClickListener {
                favouriteViewModel.addTvFavourite(tvShow.getFavTv())
                Snackbar.make(binding.root, "Added to favourites", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupUi(tvShow: Tv) {
        Glide.with(this)
            .load(IMAGE_BASE_URL +tvShow.poster_path)
            .placeholder(R.drawable.template_placeholder)
            .error(R.drawable.template_placeholder)
            .into(binding.ivPoster)

        binding.tvTvTitle.text = tvShow.name
        binding.tvStars.text = tvShow.vote_average.toString()
        binding.tvVotes.text = tvShow.vote_count.toString()
        binding.tvDescription.text = tvShow.overview
    }
}