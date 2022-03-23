package com.bintangfajarianto.submission2.adapter

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bintangfajarianto.submission2.model.User
import com.bintangfajarianto.submission2.ui.detail.UserConnectionFragment

class UserConnectionAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    var listUser: ArrayList<User> = arrayListOf()

    override fun createFragment(position: Int): Fragment {
        val fragment = UserConnectionFragment()
        fragment.arguments = Bundle().apply {
            putInt(UserConnectionFragment.SECTION_NUMBER, position + 1)
            putParcelableArrayList(UserConnectionFragment.LIST_USER, listUser)
        }
        return fragment
    }

    override fun getItemCount(): Int = 2
}