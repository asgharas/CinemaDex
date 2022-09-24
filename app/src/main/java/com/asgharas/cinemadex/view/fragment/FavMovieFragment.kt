package com.asgharas.cinemadex.view.fragment

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.asgharas.cinemadex.databinding.FragmentFavMovieBinding
import com.asgharas.cinemadex.model.data.Movie
import com.asgharas.cinemadex.view.adapter.MovieAdapter
import com.asgharas.cinemadex.viewmodel.FavouriteViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavMovieFragment : Fragment() {

    private lateinit var binding: FragmentFavMovieBinding
    private lateinit var favViewModel: FavouriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavMovieBinding.inflate(inflater, container, false)
        val rvAdapter = MovieAdapter(requireContext(), ::handleMovieClick, ::onReachedBottom, ::handleLongClick)
        favViewModel = ViewModelProvider(this)[FavouriteViewModel::class.java]
        favViewModel.getMovieFavourites()

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = rvAdapter
        }
        favViewModel.movieFavourites.observe(this.viewLifecycleOwner) {
            rvAdapter.differ.submitList(it)
        }

        return binding.root
    }

    private fun handleMovieClick(movie: Movie) {
        // do nothing
    }

    private fun onReachedBottom() {
        //do nothing
    }

    private fun handleLongClick(cinemaItem: Parcelable) {
        val movie = cinemaItem as Movie
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Remove Favourite")
            .setMessage("Do you want to remove ${movie.title} from Favourites?")
            .setPositiveButton("Yes") { _, _ ->
                favViewModel.removeMovieFavourite(movie)
                Snackbar.make(binding.root, "${movie.title} removed from favourites", Snackbar.LENGTH_SHORT).show()
            }
            .setNegativeButton("No") { _, _ ->
                Snackbar.make(binding.root, "Still in favourites", Snackbar.LENGTH_SHORT).show()
            }
            .show()
    }
}