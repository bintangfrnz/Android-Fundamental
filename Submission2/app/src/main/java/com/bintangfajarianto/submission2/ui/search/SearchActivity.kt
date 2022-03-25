package com.bintangfajarianto.submission2.ui.search

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bintangfajarianto.submission2.R
import com.bintangfajarianto.submission2.adapter.HomeRVAdapter
import com.bintangfajarianto.submission2.adapter.UserAdapter
import com.bintangfajarianto.submission2.databinding.ActivitySearchBinding
import com.bintangfajarianto.submission2.model.User
import com.bintangfajarianto.submission2.utils.Constants

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private val searchViewModel by viewModels<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.textInputEditText.setOnKeyListener { _, keyCode, event ->
            when {
                (keyCode == KeyEvent.KEYCODE_ENTER) && (event.action == KeyEvent.ACTION_DOWN) -> {
                    searchOnClick()
                    return@setOnKeyListener true
                }
                else -> false
            }
        }

        binding.textInputLayout.setEndIconOnClickListener {
            searchOnClick()
        }

        val username = intent.getStringExtra(USERNAME) ?: "bintangfrnz"
        searchViewModel.search(username)

        binding.actionBarLayout.actionBarBack.setOnClickListener {
            finish()
        }

        searchViewModel.textInput.observe(this) {
            binding.textInputEditText.setText(it)
        }

        searchViewModel.listUser.observe(this) {
            val listUser = ArrayList<User>()
            for (user in it) {
                Log.e("UserConnectionFragment", user.login)
                listUser.add(user)
            }
            showRecyclerView(listUser)
        }

        searchViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun searchOnClick() {
        val editText = binding.textInputEditText

        if (editText.text!!.isEmpty()) {
            Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show()
            editText.clearFocus()
        } else {
            val searchIntent = Intent(this@SearchActivity, SearchActivity::class.java)
            searchIntent.putExtra(USERNAME, editText.text!!)
            startActivity(searchIntent)
        }
    }

    private fun showRecyclerView(listUser: ArrayList<User>) {
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