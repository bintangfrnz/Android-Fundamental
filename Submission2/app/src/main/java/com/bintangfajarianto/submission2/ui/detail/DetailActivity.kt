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
        detailViewModel.findUser(user)

        binding.actionBarLayout.actionBarBack.setOnClickListener {
            finish()
        }
        binding.detailButton.setOnClickListener {
            val webviewIntent = Intent(this@DetailActivity, WebViewActivity::class.java)
            webviewIntent.putExtra(WebViewActivity.URL, detailViewModel.url)
            startActivity(webviewIntent)
        }

        detailViewModel.user.observe(this) {
            setUserData(it)
        }
        detailViewModel.isLoadingData.observe(this) {
            showLoading(it, 1)
        }

        // get rv follower
        detailViewModel.findFollower(user)
        detailViewModel.isLoadingFragment.observe(this) {
            showLoading(it, 2)
        }

        // Fragment & Tab Layout
        val listFrag = mutableListOf<Fragment>(
            UserConnectionFragment.instance(UserConnectionFragment.FOLLOWERS),
            UserConnectionFragment.instance(UserConnectionFragment.FOLLOWING)
        )

        val fragmentAdapter = FragmentAdapter(this@DetailActivity, listFrag)

        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = fragmentAdapter

        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) {tab, position ->
            tab.text = TAB_TITLES[position]
        }.attach()

        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == UserConnectionFragment.FOLLOWERS) {
                    detailViewModel.findFollower(user)
                } else {
                    detailViewModel.findFollowing(user)
                }

                detailViewModel.isLoadingFragment.observe(this@DetailActivity) {
                    showLoading(it, 2)
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
                detailViewModel.resetList()
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun setUserData(userDetail: UserDetail) {
        binding.detailName.text = userDetail.name
        binding.detailUsername.text = userDetail.login
        Glide.with(applicationContext)
            .load(userDetail.avatarUrl)
            .into(binding.detailAvatar)
        binding.detailCompany.text = userDetail.company
        binding.detailLocation.text = userDetail.location

        // Formatting number of Following, Follower and Repository
        val decimalFormat = CompactDecimalFormat.getInstance(Locale.US, CompactDecimalFormat.CompactStyle.SHORT)
        decimalFormat.maximumFractionDigits = 1

        val follower = decimalFormat.format(userDetail.followers) + " followers"
        val following = decimalFormat.format(userDetail.following) + " following"
        val repo = decimalFormat.format(userDetail.publicRepos) + " repository"
        val gist = decimalFormat.format(userDetail.publicGists) + " gist"

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