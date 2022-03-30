package com.bintangfajarianto.submission3.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bintangfajarianto.submission3.R
import com.bintangfajarianto.submission3.data.model.Profile
import com.bintangfajarianto.submission3.databinding.ActivityHomeBinding
import com.bintangfajarianto.submission3.ui.profile.ProfileActivity
import com.bintangfajarianto.submission3.ui.setting.SettingActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.myToolbar)
        supportActionBar?.title = "Github"

        // Initialize Fragment
        val fragmentAdapter = HomeAdapter(this@HomeActivity)

        // Initialize TabLayout and ViewPager
        val tabs: TabLayout = binding.tabs
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = fragmentAdapter
        TabLayoutMediator(tabs, viewPager) { tab, pos ->
            tab.text = TAB_TITLES[pos]
        }.attach()
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
                    "https://avatars.githubusercontent.com/u/75726616?v=4"
                )
                val profileIntent = Intent(this@HomeActivity, ProfileActivity::class.java)
                profileIntent.putExtra(ProfileActivity.EXTRA_PROFILE, myProfile)
                startActivity(profileIntent)
            }
            R.id.setting_icon -> {
                val settingIntent = Intent(this@HomeActivity, SettingActivity::class.java)
                startActivity(settingIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private val TAB_TITLES = arrayOf("Search", "Favorite")
    }
}