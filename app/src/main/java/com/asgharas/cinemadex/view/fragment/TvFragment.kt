package com.asgharas.cinemadex.view.fragment

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
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager

        tvViewModel.tvShowsList.observe(this.viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }
        return binding.root
    }
//
//    private fun bindObserver(
//        it: NetworkResult<DiscoverTVResponse>,
//        tvAdapter: TvAdapter
//    ) {
//        when (it) {
//            is NetworkResult.Loading -> binding.progressBarTv.isVisible = true
//            is NetworkResult.Success -> {
//                tvAdapter.differ.submitList(it.data!!.results)
//                binding.progressBarTv.isVisible = false
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
//
//    private fun connectGetTv() {
//        if (checkForInternet(requireContext())) {
//            tvViewModel.getTv()
//            binding.progressBarTv.visibility = View.VISIBLE
//            binding.tvInternetConnectionTV.visibility = View.GONE
//            binding.btnRetryConnectionTV.visibility = View.GONE
//        } else {
//            if (tvViewModel.tvShowsList.value?.data?.results?.size == 0 || tvViewModel.tvShowsList.value?.data?.results?.size == null) {
//                binding.tvInternetConnectionTV.visibility = View.VISIBLE
//                binding.btnRetryConnectionTV.visibility = View.VISIBLE
//            } else {
//                binding.tvInternetConnectionTV.visibility = View.GONE
//                binding.btnRetryConnectionTV.visibility = View.GONE
//            }
//        }
//    }

    private fun handleTvClick(tv: Tv) {
        Intent(requireContext(), SingleTvActivity::class.java).apply {
            putExtra("tv_show", tv)
            startActivity(this)
        }
    }


}