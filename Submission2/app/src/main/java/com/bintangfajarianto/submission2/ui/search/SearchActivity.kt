package com.bintangfajarianto.submission2.ui.search

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bintangfajarianto.submission2.R
import com.bintangfajarianto.submission2.adapter.UserAdapter
import com.bintangfajarianto.submission2.databinding.ActivitySearchBinding
import com.bintangfajarianto.submission2.model.User


class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private val searchViewModel by viewModels<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        // Customize SearchView Color
        val searchView = binding.searchView
        val searchIcon: ImageView = searchView.findViewById(androidx.appcompat.R.id.search_mag_icon)
        val searchHintIcon: ImageView = searchView.findViewById(androidx.appcompat.R.id.search_voice_btn)
        val closeIcon: ImageView = searchView.findViewById(androidx.appcompat.R.id.search_close_btn)
        val hintText: TextView = searchView.findViewById(androidx.appcompat.R.id.search_src_text)
//        searchIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_search))
        searchIcon.setColorFilter(ContextCompat.getColor(applicationContext, R.color.blue_light))
        searchHintIcon.setColorFilter(ContextCompat.getColor(applicationContext, R.color.blue_light))
        closeIcon.setColorFilter(ContextCompat.getColor(applicationContext, R.color.blue_light))
        hintText.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))


        // Initial search
        val username = intent.getStringExtra(USERNAME) ?: "bintangfrnz"
        binding.searchView.setQuery(username, false)
        searchViewModel.search(username)

        // Set up SearchView
        binding.searchView.setOnQueryTextListener( object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query.toString().isEmpty()) {
                    searchViewModel.reset()
                } else {
                    searchViewModel.search(query.toString())
                }
                return true
            }
        })

        binding.actionBarLayout.actionBarBack.setOnClickListener {
            finish()
        }

        searchViewModel.listUser.observe(this) {
            val listUser = ArrayList<User>()
            for (user in it)
                listUser.add(user)

            setRecyclerView(listUser)
        }

        searchViewModel.messageError.observe(this) {
            binding.errorMessage.text = it
        }

        searchViewModel.isLoading.observe(this) {
            setLoading(it)
        }
    }

    private fun setLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setRecyclerView(listUser: ArrayList<User>) {
        val rvUsers = binding.rvUsers

        // Add divider between item in recyclerview
        val div = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        div.setDrawable(ColorDrawable(ContextCompat.getColor(applicationContext, R.color.white)))
        rvUsers.addItemDecoration(div)

        rvUsers.layoutManager = LinearLayoutManager(this)
        rvUsers.adapter = UserAdapter(listUser)
    }

    companion object {
        const val USERNAME = "username"
    }
}