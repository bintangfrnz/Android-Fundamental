package com.bintangfajarianto.submission3.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bintangfajarianto.submission3.ui.home.search.SearchFragment
import com.bintangfajarianto.submission3.ui.home.favorite.FavoriteFragment

class HomeAdapter(activity: FragmentActivity)
    : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        var fragment = Fragment()
        when (position) {
            0 -> fragment = SearchFragment()
            1 -> fragment = FavoriteFragment()
        }
        return fragment
    }

    override fun getItemCount(): Int = 2
}