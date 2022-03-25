package com.bintangfajarianto.submission2.ui.detail

import android.content.Intent
import android.icu.text.CompactDecimalFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.bintangfajarianto.submission2.adapter.FragmentAdapter
import com.bintangfajarianto.submission2.databinding.ActivityDetailBinding
import com.bintangfajarianto.submission2.model.UserDetail
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

        // Initialize User Detail Profile
        detailViewModel.findUser(user)
        detailViewModel.user.observe(this) {
            setUserData(it)
        }
        detailViewModel.isLoadingData.observe(this) {
            showLoading(it, LOADING_DETAIL)
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
        detailViewModel.isLoadingFragment.observe(this) {
            showLoading(it, LOADING_FRAGMENT)
        }

        // Initialize Fragment
        val listFrag = mutableListOf<Fragment>(
            createFrag(TAB_FOLLOWERS),
            createFrag(TAB_FOLLOWING)
        )
        val fragmentAdapter = FragmentAdapter(this@DetailActivity, listFrag)

        // Initialize TabLayout and ViewPager
        val tabs: TabLayout = binding.tabs
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = fragmentAdapter

        TabLayoutMediator(tabs, viewPager) { tab, pos ->
            tab.text = TAB_TITLES[pos]
        }.attach()

        tabs.addOnTabSelectedListener( object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == TAB_FOLLOWERS)
                    detailViewModel.findFollower(user)
                else
                    detailViewModel.findFollowing(user)

                detailViewModel.isLoadingFragment.observe(this@DetailActivity) {
                    showLoading(it, LOADING_FRAGMENT)
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
                detailViewModel.resetList()
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Do Nothing
            }
        })
    }

    private fun setUserData(userDetail: UserDetail) {
        // Formatting number of Following, Follower and Repository
        val decimalFormat = CompactDecimalFormat.getInstance(Locale.US, CompactDecimalFormat.CompactStyle.SHORT)
        decimalFormat.maximumFractionDigits = 1

        val follower = decimalFormat.format(userDetail.followers) + " followers"
        val following = decimalFormat.format(userDetail.following) + " following"
        val repo = decimalFormat.format(userDetail.publicRepos) + " repository"
        val gist = decimalFormat.format(userDetail.publicGists) + " gist"

        binding.apply {
            detailName.text = userDetail.name
            detailUsername.text = userDetail.login
            Glide.with(applicationContext)
                .load(userDetail.avatarUrl)
                .into(detailAvatar)
            detailCompany.text = userDetail.company
            detailLocation.text = userDetail.location
            detailFollower.text = follower
            detailFollowing.text = following
            detailRepository.text = repo
            detailGist.text = gist
        }
    }

    private fun setRecyclerView(tab: Int, user: String) {
        if (tab == TAB_FOLLOWERS)
            detailViewModel.findFollower(user)
        else
            detailViewModel.findFollowing(user)
    }

    private fun showLoading(isLoading: Boolean, nBar: Int) {
        if (nBar == LOADING_DETAIL)
            binding.progressBar1.visibility = if (isLoading) View.VISIBLE else View.GONE
        else
            binding.progressBar2.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val TAB_FOLLOWERS = 0
        const val TAB_FOLLOWING = 1
        const val LOADING_DETAIL = 0
        const val LOADING_FRAGMENT = 1

        private val TAB_TITLES = arrayOf(
            "Follower",
            "Following"
        )

        fun createFrag(pos: Int): UserConnectionFragment =
            UserConnectionFragment().apply {
                position = pos
            }
    }
}