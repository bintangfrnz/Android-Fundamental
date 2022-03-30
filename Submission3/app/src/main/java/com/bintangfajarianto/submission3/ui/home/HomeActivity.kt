package com.bintangfajarianto.submission3.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bintangfajarianto.submission3.R
import com.bintangfajarianto.submission3.databinding.ActivityHomeBinding
import com.bintangfajarianto.submission3.data.model.Profile
import com.bintangfajarianto.submission3.ui.profile.ProfileActivity
import com.bintangfajarianto.submission3.ui.setting.SettingActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Github"
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
}