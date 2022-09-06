package com.asgharas.cinemadex.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asgharas.cinemadex.databinding.FragmentFavouriteBinding
import com.asgharas.cinemadex.view.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class FavouriteFragment : Fragment() {

    private lateinit var binding: FragmentFavouriteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)

        binding.viewPager.adapter =
            ViewPagerAdapter(childFragmentManager, lifecycle)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) {tab, position ->
            when(position){
                0 -> tab.text = "⭐ Movies"
                else -> tab.text = "⭐ Tv Shows"
            }
        }.attach()

        return binding.root
    }

}