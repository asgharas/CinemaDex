package com.asgharas.cinemadex.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.asgharas.cinemadex.view.fragment.FavMovieFragment
import com.asgharas.cinemadex.view.fragment.FavTvFragment

class ViewPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> FavMovieFragment()
            1 -> FavTvFragment()
            else -> FavMovieFragment()
        }
}