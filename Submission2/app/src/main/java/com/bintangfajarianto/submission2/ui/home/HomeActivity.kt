package com.bintangfajarianto.submission2.ui.home

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bintangfajarianto.submission2.R
import com.bintangfajarianto.submission2.adapter.HomeRVAdapter
import com.bintangfajarianto.submission2.databinding.ActivityHomeBinding
import com.bintangfajarianto.submission2.model.Profile
import com.bintangfajarianto.submission2.ui.profile.ProfileActivity
import com.bintangfajarianto.submission2.ui.search.SearchActivity
import com.bintangfajarianto.submission2.utils.Constants

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private var listUser: ArrayList<Profile> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Github"

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

        getListUser()
        showRecyclerList()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.profile_icon -> {
                val myProfile = Profile(
                    "Bintang Fajarianto",
                    "bintangfrnz_",
                    "Kampus Ganesha",
                    "Bandung",
                    R.drawable.user0
                )
                val profileIntent = Intent(this@HomeActivity, ProfileActivity::class.java)
                profileIntent.putExtra(ProfileActivity.EXTRA_PROFILE, myProfile)
                startActivity(profileIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun searchOnClick() {
        val editText = binding.textInputEditText

        if (editText.text!!.isEmpty()) {
            Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show()
            editText.clearFocus()
        } else {
            val searchIntent = Intent(this@HomeActivity, SearchActivity::class.java)
            searchIntent.putExtra(SearchActivity.USERNAME, editText.text!!)

            editText.setText(Constants.BLANK)
            editText.clearFocus()

            startActivity(searchIntent)
        }
    }

    private fun showRecyclerList() {
        val rvUsers = binding.rvUsers

        // Add divider between item in recyclerview
        val div = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        div.setDrawable(ColorDrawable(ContextCompat.getColor(applicationContext, R.color.white)))
        rvUsers.addItemDecoration(div)

        rvUsers.layoutManager = LinearLayoutManager(this)
        val listUserAdapter = HomeRVAdapter(listUser)
        rvUsers.adapter = listUserAdapter
    }

    private fun getListUser() {
        val usernames = resources.getStringArray(R.array.username)
        val names = resources.getStringArray(R.array.name)
        val locations = resources.getStringArray(R.array.location)
        val companies = resources.getStringArray(R.array.company)
        val avatars = resources.obtainTypedArray(R.array.avatar)

        for (pos in usernames.indices) {
            val user = Profile(
                names[pos],
                usernames[pos],
                companies[pos],
                locations[pos],
                avatars.getResourceId(pos, pos)
            )
            listUser.add(user)
        }
        avatars.recycle()
    }
}