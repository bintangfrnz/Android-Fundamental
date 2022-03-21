package com.bintangfajarianto.submission1.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bintangfajarianto.submission1.R
import com.bintangfajarianto.submission1.adapter.HomeRVAdapter
import com.bintangfajarianto.submission1.databinding.ActivityHomeBinding
import com.bintangfajarianto.submission1.model.Profile
import com.bintangfajarianto.submission1.model.User

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var rvUsers: RecyclerView
    private var listUser: ArrayList<User> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Github"

        rvUsers = binding.rvUsers
        listUser.addAll(getListUser(resources))
        showRecyclerList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.profile_icon -> {
                val myProfile = Profile(
                    "Bintang Fajarianto",
                    "bintangfrnz_",
                    "bintangfajarianto@gmail.com",
                    R.drawable.user0
                )
                val profileIntent = Intent(this@HomeActivity, ProfileActivity::class.java)
                profileIntent.putExtra(ProfileActivity.EXTRA_PROFILE, myProfile)
                startActivity(profileIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showRecyclerList() {
        // Add divider between item in recyclerview
        val div = DividerItemDecoration(this,DividerItemDecoration.VERTICAL)
        div.setDrawable(ColorDrawable(ContextCompat.getColor(applicationContext, R.color.white)))
        rvUsers.addItemDecoration(div)

        rvUsers.layoutManager = LinearLayoutManager(this)
        val listUserAdapter = HomeRVAdapter(listUser)
        rvUsers.adapter = listUserAdapter
    }

    private fun Array<String>.toIntArray() = this.map { it.toInt() }.toTypedArray()

    // Called in DetailUserActivity as well
    @SuppressLint("Recycle")
    fun getListUser(resources: Resources) : ArrayList<User> {
        val list: ArrayList<User> = arrayListOf()
        val usernames = resources.getStringArray(R.array.username)
        val names = resources.getStringArray(R.array.name)
        val locations = resources.getStringArray(R.array.location)
        val repositories = resources.getStringArray(R.array.repository).toIntArray()
        val companies = resources.getStringArray(R.array.company)
        val followers = resources.getStringArray(R.array.followers).toIntArray()
        val following = resources.getStringArray(R.array.following).toIntArray()
        val avatars = resources.obtainTypedArray(R.array.avatar)

        for (pos in usernames.indices) {
            val user = User(
                usernames[pos],
                names[pos],
                locations[pos],
                repositories[pos],
                companies[pos],
                followers[pos],
                following[pos],
                avatars.getResourceId(pos, pos),
            )
            list.add(user)
        }
        return list
    }
}