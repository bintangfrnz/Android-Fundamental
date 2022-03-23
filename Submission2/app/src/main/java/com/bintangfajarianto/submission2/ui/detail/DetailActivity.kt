package com.bintangfajarianto.submission2.ui.detail

import android.content.Intent
import android.icu.text.CompactDecimalFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.bintangfajarianto.submission2.adapter.UserConnectionAdapter
import com.bintangfajarianto.submission2.databinding.ActivityDetailBinding
import com.bintangfajarianto.submission2.model.User
import com.bintangfajarianto.submission2.ui.webview.WebViewActivity
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val user = intent.getStringExtra(EXTRA_USERNAME) as String
        detailViewModel.findUser(user)

        detailViewModel.user.observe(this) {
            setUserData(it)
        }
        detailViewModel.isLoading1.observe(this) {
            showLoading(it, 1)
        }
        detailViewModel.isLoading2.observe(this) {
            showLoading(it, 2)
        }

        binding.actionBarLayout.actionBarBack.setOnClickListener {
            finish()
        }

        binding.detailButton.setOnClickListener {
            val webviewIntent = Intent(this@DetailActivity, WebViewActivity::class.java)
            webviewIntent.putExtra(WebViewActivity.URL, detailViewModel.url)
            startActivity(webviewIntent)
        }

        // Fragment & Tab Layout
        val userConnectionAdapter = UserConnectionAdapter(this)

        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = userConnectionAdapter

        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) {tab, position ->
            tab.text = TAB_TITLES[position]
        }.attach()

    }

    private fun setUserData(user: User) {
        binding.detailName.text = user.name
        binding.detailUsername.text = user.login
        Glide.with(applicationContext)
            .load(user.avatarUrl)
            .into(binding.detailAvatar)
        binding.detailCompany.text = user.company
        binding.detailLocation.text = user.location

        // Formatting number of Following, Follower and Repository
        val decimalFormat = CompactDecimalFormat.getInstance(Locale.US, CompactDecimalFormat.CompactStyle.SHORT)
        decimalFormat.maximumFractionDigits = 1

        val follower = decimalFormat.format(user.followers) + " followers"
        val following = decimalFormat.format(user.following) + " following"
        val repo = decimalFormat.format(user.publicRepos) + " repository"
        val gist = decimalFormat.format(user.publicGists) + " gist"

        binding.detailFollower.text = follower
        binding.detailFollowing.text = following
        binding.detailRepository.text = repo
        binding.detailGist.text = gist
    }

    private fun showLoading(isLoading: Boolean, nBar: Int) {
        if (nBar == 1)
            binding.progressBar1.visibility = if (isLoading) View.VISIBLE else View.GONE
        else
            binding.progressBar2.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"

        private val TAB_TITLES = arrayOf(
            "Follower",
            "Following"
        )
    }
}