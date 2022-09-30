package com.asgharas.cinemadex.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.asgharas.cinemadex.databinding.LoadingLayoutBinding

class LoaderAdapter : LoadStateAdapter<LoaderAdapter.LoaderViewHolder>() {

    inner class LoaderViewHolder(private val binding: LoadingLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState){
            binding.loadingProgressBar.isVisible = loadState is LoadState.Loading
            binding.errorTv.isVisible = loadState is LoadState.Error
        }
    }

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        val binding = LoadingLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoaderViewHolder(binding)
    }
}