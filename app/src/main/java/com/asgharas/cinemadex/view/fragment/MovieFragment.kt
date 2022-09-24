package com.asgharas.cinemadex.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.asgharas.cinemadex.databinding.FragmentMovieBinding
import com.asgharas.cinemadex.model.data.Movie
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
        binding.movieRecyclerView.adapter = adapter


//        binding.tvInternetConnectionMovie.visibility = View.GONE
//        binding.btnRetryConnectionMovie.visibility = View.GONE

//        movieViewModel.getMovie()
//        binding.btnRetryConnectionMovie.setOnClickListener {  }

        movieViewModel.moviesList.observe(this.viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }

        return binding.root
    }

//    private fun bindObserver(
//        it: NetworkResult<DiscoverMovieResponse>,
//        movieAdapter: MovieAdapter
//    ) {
//        when (it) {
//            is NetworkResult.Loading -> binding.progressBar.isVisible = true
//            is NetworkResult.Success -> {
//                movieAdapter.differ.submitList(it.data!!.results)
//                binding.progressBar.isVisible = false
//            }
//            is NetworkResult.Error -> {
//                AlertDialog.Builder(requireContext())
//                    .setTitle("Error Occurred")
//                    .setMessage(it.message)
//                    .setIcon(R.drawable.ic_error)
//                    .setNeutralButton("OK!", null)
//                    .show()
//            }
//        }
//    }

//    private fun connectGetMovie() {
//        if (checkForInternet(requireContext())) {
//            movieViewModel.getMovie()
//            binding.progressBar.visibility = View.VISIBLE
//            binding.tvInternetConnectionMovie.visibility = View.GONE
//            binding.btnRetryConnectionMovie.visibility = View.GONE
//        } else {
//            if (movieViewModel.moviesList.value?.data?.results?.size == 0 || movieViewModel.moviesList.value?.data?.results?.size == null) {
//                binding.tvInternetConnectionMovie.visibility = View.VISIBLE
//                binding.btnRetryConnectionMovie.visibility = View.VISIBLE
//            } else {
//                binding.tvInternetConnectionMovie.visibility = View.GONE
//                binding.btnRetryConnectionMovie.visibility = View.GONE
//            }
//        }
//    }

    private fun handleMovieClick(movie: Movie) {
        Intent(requireContext(), SingleMovieActivity::class.java).apply {
            putExtra("movie_extra", movie)
            startActivity(this)
        }
    }

}