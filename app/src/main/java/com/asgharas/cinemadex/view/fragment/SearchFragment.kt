package com.asgharas.cinemadex.view.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.asgharas.cinemadex.R
import com.asgharas.cinemadex.databinding.FragmentSearchBinding
import com.asgharas.cinemadex.model.data.DiscoverMovieResponse
import com.asgharas.cinemadex.model.data.DiscoverTVResponse
import com.asgharas.cinemadex.model.data.Movie
import com.asgharas.cinemadex.model.data.Tv
import com.asgharas.cinemadex.utils.network.NetworkResult
import com.asgharas.cinemadex.view.activity.SingleMovieActivity
import com.asgharas.cinemadex.view.activity.SingleTvActivity
import com.asgharas.cinemadex.view.adapter.MovieSearchAdapter
import com.asgharas.cinemadex.view.adapter.TvSearchAdapter
import com.asgharas.cinemadex.viewmodel.SearchViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchViewModel: SearchViewModel
    private val tvSearchAdapter by lazy {
        TvSearchAdapter(requireContext(), ::handleTvClick)
    }

    private val movieSearchAdapter by lazy {
        MovieSearchAdapter(requireContext(), ::handleMovieClick)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        binding.tvRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.tvRecyclerView.adapter = movieSearchAdapter

        binding.btnSearch.setOnClickListener {
            if (checkEditText()) searchClicked()
        }


        binding.progressBar2.visibility = View.GONE

        searchViewModel.movieQueryResult.observe(this.viewLifecycleOwner) {
            bindMovieObserver(it, movieSearchAdapter)
            searchOff()
        }
        searchViewModel.tvQueryResult.observe(this.viewLifecycleOwner) {
            bindTvObserver(it, tvSearchAdapter)
            searchOff()
        }

        return binding.root
    }

    private fun bindTvObserver(
        it: NetworkResult<DiscoverTVResponse>,
        tvSearchAdapter: TvSearchAdapter
    ) {
        when (it) {
            is NetworkResult.Loading -> {
                binding.progressBar2.isVisible = true
            }
            is NetworkResult.Success -> {
                tvSearchAdapter.differ.submitList(it.data!!.results)
                binding.tvRecyclerView.adapter = tvSearchAdapter
            }
            is NetworkResult.Error -> {
                AlertDialog.Builder(requireContext())
                    .setTitle("Error Occurred")
                    .setMessage(it.message)
                    .setIcon(R.drawable.ic_error)
                    .setNeutralButton("OK!", null)
                    .show()
            }
        }
    }
    private fun bindMovieObserver(
        it: NetworkResult<DiscoverMovieResponse>,
        movieSearchAdapter: MovieSearchAdapter
    ) {
        when (it) {
            is NetworkResult.Loading -> {
                binding.progressBar2.isVisible = true
            }
            is NetworkResult.Success -> {
                movieSearchAdapter.differ.submitList(it.data!!.results)
                binding.tvRecyclerView.adapter = movieSearchAdapter
            }
            is NetworkResult.Error -> {
                AlertDialog.Builder(requireContext())
                    .setTitle("Error Occurred")
                    .setMessage(it.message)
                    .setIcon(R.drawable.ic_error)
                    .setNeutralButton("OK!", null)
                    .show()
            }
        }
    }

    private fun searchClicked() {
        val searchQuery = binding.etSearch.text.toString()

        if (binding.movieRadio.isChecked) {
            searchViewModel.searchMovies(searchQuery)
        } else if (binding.tvRadio.isChecked) {
            binding.tvRecyclerView.adapter = tvSearchAdapter
            searchViewModel.searchTv(searchQuery)
        }
        searchOn()
    }

    private fun searchOn() {
        binding.progressBar2.visibility = View.VISIBLE
    }

    private fun searchOff() {
        binding.progressBar2.visibility = View.GONE
        hideKeyboard()
    }

    private fun checkEditText(): Boolean {
        return if (binding.etSearch.text.toString().isNotEmpty()) true
        else {
            Snackbar.make(
                requireContext(),
                binding.root,
                "Fill in the Search Bar",
                Snackbar.LENGTH_SHORT
            ).show()
            false
        }
    }

    private fun handleMovieClick(movie: Movie) {
        @Suppress("SENSELESS_COMPARISON")
        if (movie.poster_path == null) return
        Intent(requireContext(), SingleMovieActivity::class.java).apply {
            putExtra("movie_extra", movie)
            startActivity(this)
        }
    }

    private fun handleTvClick(tv: Tv) {
        @Suppress("SENSELESS_COMPARISON")
        if (tv.poster_path == null) return
        Intent(requireContext(), SingleTvActivity::class.java).apply {
            putExtra("tv_show", tv)
            startActivity(this)
        }
    }

    private fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }


    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

