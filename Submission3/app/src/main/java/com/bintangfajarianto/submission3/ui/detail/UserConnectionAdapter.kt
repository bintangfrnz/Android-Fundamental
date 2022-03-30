package com.bintangfajarianto.submission3.ui.detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class UserConnectionAdapter(activity: FragmentActivity)
    : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        var fragment = Fragment()
        when (position) {
            0 -> fragment = FollowerFragment()
            1 -> fragment = FollowingFragment()
        }
        return fragment
    }
    override fun getItemCount(): Int = 2
}