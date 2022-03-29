package com.bintangfajarianto.submission2.ui.detail

import android.content.Intent
import android.icu.text.CompactDecimalFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.bintangfajarianto.submission2.adapter.FragmentAdapter
import com.bintangfajarianto.submission2.databinding.ActivityDetailBinding
import com.bintangfajarianto.submission2.model.UserDetail
import com.bintangfajarianto.submission2.ui.webview.WebViewActivity
import com.bintangfajarianto.submission2.utils.Constants
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

        // Initialize User Detail Profile
        detailViewModel.findUser(user)
        detailViewModel.user.observe(this) {
            setUserData(it)
        }
        detailViewModel.isLoadingData.observe(this) {
            setLoading(it)
        }

        // Set up Button (need url to user github)
        binding.actionBarLayout.actionBarBack.setOnClickListener {
            finish()
        }
        binding.detailButton.setOnClickListener {
            val webviewIntent = Intent(this@DetailActivity, WebViewActivity::class.java)
            webviewIntent.putExtra(WebViewActivity.URL, detailViewModel.url)
            startActivity(webviewIntent)
        }

        // Initialize RecyclerView
        detailViewModel.currentTab.observe(this) {
            setRecyclerView(it, user)
        }

        // Initialize Fragment
        val fragmentAdapter = FragmentAdapter(this@DetailActivity)

        // Initialize TabLayout and ViewPager
        val tabs: TabLayout = binding.tabs
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = fragmentAdapter
        TabLayoutMediator(tabs, viewPager) { tab, pos ->
            tab.text = TAB_TITLES[pos]
        }.attach()

        // Set OnTabSelect
        tabs.addOnTabSelectedListener( object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == Constants.TAB_FOLLOWERS)
                    detailViewModel.setTab(Constants.TAB_FOLLOWERS)
                else
                    detailViewModel.setTab(Constants.TAB_FOLLOWING)
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Do Nothing
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Do Nothing
            }
        })
    }

    private fun setUserData(userDetail: UserDetail?) {
        // Formatting number of Following, Follower and Repository
        val decimalFormat = CompactDecimalFormat.getInstance(Locale.US, CompactDecimalFormat.CompactStyle.SHORT)
        decimalFormat.maximumFractionDigits = 1

        val name = if (userDetail?.name.isNullOrEmpty()) Constants.DASH else userDetail?.name
        val company = if (userDetail?.company.isNullOrEmpty()) Constants.DASH else userDetail?.company
        val location = if (userDetail?.location.isNullOrEmpty()) Constants.DASH else userDetail?.location
        val follower = decimalFormat.format(userDetail?.followers) + " followers"
        val following = decimalFormat.format(userDetail?.following) + " following"
        val repo = decimalFormat.format(userDetail?.publicRepos) + " repository"
        val gist = decimalFormat.format(userDetail?.publicGists) + " gist"

        binding.apply {
            detailName.text = name
            detailUsername.text = userDetail?.login
            Glide.with(applicationContext)
                .load(userDetail?.avatarUrl)
                .into(detailAvatar)
            detailCompany.text = company
            detailLocation.text = location
            detailFollower.text = follower
            detailFollowing.text = following
            detailRepository.text = repo
            detailGist.text = gist
        }
    }

    private fun setRecyclerView(tab: Int, user: String) {
        if (!detailViewModel.isAllLoaded) {
            if (tab == Constants.TAB_FOLLOWERS)
                detailViewModel.findFollower(user)
            else
                detailViewModel.findFollowing(user)
        }
    }

    private fun setLoading(isLoading: Boolean) {
        binding.progressBarDetail.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"

        private val TAB_TITLES = arrayOf(
            "Follower",
            "Following"
        )
    }
}