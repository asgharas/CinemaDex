package com.asgharas.cinemadex.view.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.asgharas.cinemadex.databinding.FragmentMovieBinding
import com.asgharas.cinemadex.model.data.Movie
import com.asgharas.cinemadex.paging.LoaderAdapter
import com.asgharas.cinemadex.view.activity.SingleMovieActivity
import com.asgharas.cinemadex.view.adapter.MoviePagingAdapter
import com.asgharas.cinemadex.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment() {

    private lateinit var movieViewModel: MovieViewModel
    private lateinit var binding: FragmentMovieBinding
    private lateinit var adapter: MoviePagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieBinding.inflate(inflater, container, false)
        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]

        adapter = MoviePagingAdapter(requireContext(), ::handleMovieClick)
        binding.movieRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.movieRecyclerView.setHasFixedSize(true)
        binding.movieRecyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
            header = LoaderAdapter(),
            footer = LoaderAdapter()
        )


        movieViewModel.moviesList.observe(this.viewLifecycleOwner) {
            Log.d("imtihan", "onCreateView: $it")
            adapter.submitData(lifecycle, it)
        }

        return binding.root
    }

    private fun handleMovieClick(movie: Movie) {
        Intent(requireContext(), SingleMovieActivity::class.java).apply {
            putExtra("movie_extra", movie)
            startActivity(this)
        }
    }

}