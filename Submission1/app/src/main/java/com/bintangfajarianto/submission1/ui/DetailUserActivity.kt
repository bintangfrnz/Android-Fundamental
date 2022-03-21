package com.bintangfajarianto.submission1.ui

import android.icu.text.CompactDecimalFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bintangfajarianto.submission1.adapter.DetailRVAdapter
import com.bintangfajarianto.submission1.databinding.ActivityDetailUserBinding
import com.bintangfajarianto.submission1.model.User
import com.bumptech.glide.Glide
import java.util.*

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var rvUsers: RecyclerView
    private var listUser: ArrayList<User> = arrayListOf()

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val back = binding.actionBarLayout.actionBarBack
        val name = binding.detailName
        val username = binding.detailUsername
        val avatar = binding.detailAvatar
        val company = binding.detailCompany
        val location = binding.detailLocation
        val repository = binding.detailRepository
        val follower = binding.detailFollower
        val following = binding.detailFollowing

        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User

        back.setOnClickListener {
            finish()
        }
        name.text = user.name
        username.text = user.username
        Glide.with(applicationContext)
            .load(user.avatar)
            .into(avatar)
        company.text = user.company
        location.text = user.location

        // Formatting number of Following, Follower and Repository
        val decimalFormat = CompactDecimalFormat.getInstance(Locale.US, CompactDecimalFormat.CompactStyle.SHORT)
        decimalFormat.maximumFractionDigits = 1

        val repo = decimalFormat.format(user.repository) + " repository"
        repository.text = repo
        follower.text = decimalFormat.format(user.followers)
        following.text = decimalFormat.format(user.following)

        rvUsers = binding.rvUsersMini
        listUser.addAll(HomeActivity().getListUser(resources).filter { it.name != user.name })
        showRecyclerList()
    }

    private fun showRecyclerList() {
        rvUsers.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val listUserAdapter = DetailRVAdapter(listUser, this)
        rvUsers.adapter = listUserAdapter
    }
}