package com.asgharas.cinemadex.view.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.asgharas.cinemadex.databinding.FragmentMovieBinding
import com.asgharas.cinemadex.model.data.Movie
import com.asgharas.cinemadex.view.activity.SingleMovieActivity
import com.asgharas.cinemadex.view.activity.TAG
import com.asgharas.cinemadex.view.adapter.MovieAdapter
import com.asgharas.cinemadex.view.listeners.LongClickListener
import com.asgharas.cinemadex.view.listeners.MovieClickListener
import com.asgharas.cinemadex.view.listeners.ReachedBottomListener
import com.asgharas.cinemadex.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment(), MovieClickListener, ReachedBottomListener, LongClickListener {

    private lateinit var movieViewModel: MovieViewModel
    private lateinit var binding: FragmentMovieBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieBinding.inflate(inflater, container, false)
        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        val movieAdapter = MovieAdapter(requireContext(), this, this, this)
        val movieRecyclerView = binding.movieRecyclerView
        movieRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        movieRecyclerView.adapter = movieAdapter

        movieViewModel.moviesList.observe(this.viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.progressBar.visibility = View.GONE
                movieAdapter.differ.submitList(it)
            } else {
                binding.progressBar.visibility = View.VISIBLE
            }
        }

        return binding.root
    }

    override fun handleMovieClick(movie: Movie) {
        Intent(requireContext(), SingleMovieActivity::class.java).apply {
            putExtra("movie_extra", movie)
            startActivity(this)
        }
    }

    override fun onReachedBottom() {
        Log.d(TAG, "onReachedBottom: REACHED BOTTOM")
        binding.progressBar.visibility = View.VISIBLE
        movieViewModel.loadNextPage()
    }

    override fun handleLongClick(cinemaItem: Parcelable) {
        //
    }
}