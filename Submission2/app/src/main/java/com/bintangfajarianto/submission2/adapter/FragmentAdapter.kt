package com.bintangfajarianto.submission2.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentAdapter(activity: FragmentActivity, private val listFrag: MutableList<Fragment>)
    : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment = listFrag[position]
    override fun getItemCount(): Int = 2
}