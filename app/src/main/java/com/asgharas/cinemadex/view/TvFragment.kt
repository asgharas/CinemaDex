package com.asgharas.cinemadex.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.asgharas.cinemadex.databinding.FragmentTvBinding
import com.asgharas.cinemadex.model.data.Tv
import com.asgharas.cinemadex.viewmodel.TvViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvFragment : Fragment(), TvClickListener, ReachedBottomListener {

    private lateinit var binding: FragmentTvBinding
    private lateinit var tvViewModel: TvViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTvBinding.inflate(inflater, container, false)
        tvViewModel = ViewModelProvider(this)[TvViewModel::class.java]

        val tvAdapter = TvAdapter(requireContext(), this, this)
        val layoutManager = GridLayoutManager(requireContext(), 3)
        val recyclerView = binding.recyclerViewTv
        recyclerView.adapter = tvAdapter
        recyclerView.layoutManager = layoutManager
        tvViewModel.tvShowsList.observe(this.viewLifecycleOwner) {
            if(it.isNotEmpty()){
                binding.progressBarTv.visibility = View.GONE
                tvAdapter.differ.submitList(it)
            } else binding.progressBarTv.visibility = View.VISIBLE
        }
        return binding.root
    }

    override fun handleTvClick(tv: Tv) {
        Intent(requireContext(), SingleTvActivity::class.java).apply {
            putExtra("tv_show", tv)
            startActivity(this)
        }
    }

    override fun onReachedBottom() {
        binding.progressBarTv.visibility = View.VISIBLE
        tvViewModel.loadNextPage()
    }
}