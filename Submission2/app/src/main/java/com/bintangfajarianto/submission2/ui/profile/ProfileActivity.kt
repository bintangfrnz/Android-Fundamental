package com.bintangfajarianto.submission2.ui.profile

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bintangfajarianto.submission2.databinding.ActivityProfileBinding
import com.bintangfajarianto.submission2.model.Profile
import com.bumptech.glide.Glide

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val myProfile = intent.getParcelableExtra<Profile>(EXTRA_PROFILE) as Profile
        setProfile(myProfile)

        binding.profileButton.setOnClickListener {
            var uri = Uri.parse("http://instagram.com/_u/${myProfile.username}/")
            var igIntent = Intent(Intent.ACTION_VIEW, uri)
            igIntent.setPackage("com.instagram.android")

            try {
                startActivity(igIntent)
            } catch (e: ActivityNotFoundException) {
                uri = Uri.parse("http://instagram.com/${myProfile.username}/")
                igIntent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(igIntent)
            }
        }

        binding.actionBarLayout.actionBarBack.setOnClickListener {
            finish()
        }
    }

    private fun setProfile(profile: Profile) {
        binding.profileName.text = profile.fullname
        binding.profileUsername.text = profile.username
        binding.profileCompany.text = profile.company
        binding.profileLocation.text = profile.location
        Glide.with(applicationContext)
            .load(profile.avatar)
            .into(binding.profileAvatar)
    }

    companion object {
        const val EXTRA_PROFILE = "extra_profile"
    }
}