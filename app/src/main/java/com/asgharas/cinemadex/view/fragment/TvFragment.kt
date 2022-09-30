package com.asgharas.cinemadex.view.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.GridLayoutManager
import com.asgharas.cinemadex.databinding.FragmentTvBinding
import com.asgharas.cinemadex.model.data.Tv
import com.asgharas.cinemadex.paging.LoaderAdapter
import com.asgharas.cinemadex.view.activity.SingleTvActivity
import com.asgharas.cinemadex.view.adapter.TvPagingAdapter
import com.asgharas.cinemadex.viewmodel.TvViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvFragment : Fragment() {

    private lateinit var binding: FragmentTvBinding
    private lateinit var tvViewModel: TvViewModel
    private lateinit var adapter: TvPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTvBinding.inflate(inflater, container, false)
        tvViewModel = ViewModelProvider(this)[TvViewModel::class.java]
        adapter = TvPagingAdapter(requireContext(), ::handleTvClick)
        val layoutManager = GridLayoutManager(requireContext(), 3)
        val recyclerView = binding.recyclerViewTv
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
            header = LoaderAdapter(),
            footer = LoaderAdapter()
        )
        recyclerView.layoutManager = layoutManager

        tvViewModel.tvShowsList.observe(this.viewLifecycleOwner) {
            Log.d("imtihan", "onCreateView: $it")
            adapter.submitData(lifecycle, it)
        }
        return binding.root
    }


    private fun handleTvClick(tv: Tv) {
        Intent(requireContext(), SingleTvActivity::class.java).apply {
            putExtra("tv_show", tv)
            startActivity(this)
        }
    }


}