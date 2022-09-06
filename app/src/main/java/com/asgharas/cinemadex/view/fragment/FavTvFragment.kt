package com.asgharas.cinemadex.view.fragment

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.asgharas.cinemadex.databinding.FragmentFavTvBinding
import com.asgharas.cinemadex.model.data.Tv
import com.asgharas.cinemadex.view.adapter.TvAdapter
import com.asgharas.cinemadex.view.listeners.LongClickListener
import com.asgharas.cinemadex.view.listeners.ReachedBottomListener
import com.asgharas.cinemadex.view.listeners.TvClickListener
import com.asgharas.cinemadex.viewmodel.FavouriteViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavTvFragment : Fragment(), TvClickListener, ReachedBottomListener, LongClickListener {

    private lateinit var binding: FragmentFavTvBinding
    private lateinit var rvAdapter: TvAdapter
    private lateinit var favViewModel: FavouriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavTvBinding.inflate(inflater, container, false)
        rvAdapter = TvAdapter(requireContext(), this, this, this)
        favViewModel = ViewModelProvider(this)[FavouriteViewModel::class.java]
        favViewModel.getTvFavourites()

        binding.recyclerView.apply {
            this.adapter = rvAdapter
            this.layoutManager = GridLayoutManager(requireContext(), 3)
        }
        favViewModel.tvFavourites.observe(this.viewLifecycleOwner) {
//            Log.d("TSTR", "onCreateView: ${it.size}")
            rvAdapter.differ.submitList(it)
        }

        return binding.root
    }

    override fun onReachedBottom() {
        // do nothing
    }

    override fun handleTvClick(tv: Tv) {
        //do nothing
    }

    override fun handleLongClick(cinemaItem: Parcelable) {
        val tvShow = cinemaItem as Tv

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Remove Favourite")
            .setMessage("Do you want to remove ${tvShow.name} from Favourites?")
            .setPositiveButton("Yes") { _, _ ->
                favViewModel.removeTvFavourite(tvShow)
                Snackbar.make(binding.root, "${tvShow.name} removed from favourites", Snackbar.LENGTH_SHORT).show()
            }
            .setNegativeButton("No") { _, _ ->
                Snackbar.make(binding.root, "Still in favourites", Snackbar.LENGTH_SHORT).show()
            }
            .show()
    }
}