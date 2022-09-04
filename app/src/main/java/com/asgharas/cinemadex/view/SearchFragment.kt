package com.asgharas.cinemadex.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.asgharas.cinemadex.databinding.FragmentSearchBinding
import com.asgharas.cinemadex.model.data.Movie
import com.asgharas.cinemadex.model.data.Tv
import com.asgharas.cinemadex.viewmodel.SearchViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(), TvClickListener, MovieClickListener {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchViewModel: SearchViewModel
    private val tvSearchAdapter by lazy{
        TvSearchAdapter(requireContext(), this)
    }
    private val movieSearchAdapter by lazy {
        MovieSearchAdapter(requireContext(), this)
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
            if(checkEditText()) searchClicked()
        }


        binding.progressBar2.visibility = View.GONE
        
        searchViewModel.movieQueryResult.observe(this.viewLifecycleOwner) {
            if(it.isNotEmpty()){
                binding.tvRecyclerView.adapter = movieSearchAdapter
                movieSearchAdapter.differ.submitList(it)
            } else {
                binding.etSearch.visibility = View.VISIBLE
            }
            searchOff()
        }
        searchViewModel.tvQueryResult.observe(this.viewLifecycleOwner) {
            if(it.isNotEmpty()){
                binding.tvRecyclerView.adapter = tvSearchAdapter
                tvSearchAdapter.differ.submitList(it)
            }
            searchOff()
        }

        return binding.root
    }

    private fun searchClicked() {
        val searchQuery = binding.etSearch.text.toString()

        if(binding.movieRadio.isChecked) {
            searchViewModel.searchMovies(searchQuery)
        } else if(binding.tvRadio.isChecked) {
            binding.tvRecyclerView.adapter = tvSearchAdapter
            searchViewModel.searchTv(searchQuery)
        }
        searchOn()
    }

    private fun searchOn(){
        binding.progressBar2.visibility = View.VISIBLE
    }

    private fun searchOff(){
        binding.progressBar2.visibility = View.GONE
    }

    private fun checkEditText(): Boolean {
        return if(binding.etSearch.text.toString().isNotEmpty()) true
        else {
            Snackbar.make(requireContext(), binding.root, "Fill in the Search Bar", Snackbar.LENGTH_SHORT).show()
            false
        }
    }

    override fun handleMovieClick(movie: Movie) {
        @Suppress("SENSELESS_COMPARISON")
        if (movie.poster_path == null) return
        Intent(requireContext(), SingleMovieActivity::class.java).apply {
            putExtra("movie_extra", movie)
            startActivity(this)
        }
    }

    override fun handleTvClick(tv: Tv) {
        @Suppress("SENSELESS_COMPARISON")
        if(tv.poster_path == null) return
        Intent(requireContext(), SingleTvActivity::class.java).apply {
            putExtra("tv_show", tv)
            startActivity(this)
        }
    }
}

