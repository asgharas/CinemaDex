package com.asgharas.cinemadex.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asgharas.cinemadex.R
import com.asgharas.cinemadex.databinding.ActivitySingleTvBinding
import com.asgharas.cinemadex.model.data.Tv
import com.asgharas.cinemadex.other.IMAGE_BASE_URL
import com.bumptech.glide.Glide

class SingleTvActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingleTvBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleTvBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val tvShow: Tv? = intent.getParcelableExtra("tv_show")

        if(tvShow != null) setupUi(tvShow)
    }

    private fun setupUi(tvShow: Tv) {
        Glide.with(this)
            .load(IMAGE_BASE_URL+tvShow.poster_path)
            .placeholder(R.drawable.template_placeholder)
            .error(R.drawable.template_placeholder)
            .into(binding.ivPoster)

        binding.tvTvTitle.text = tvShow.name
        binding.tvStars.text = tvShow.vote_average.toString()
        binding.tvVotes.text = tvShow.vote_count.toString()
        binding.tvDescription.text = tvShow.overview
    }
}