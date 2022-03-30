package com.bintangfajarianto.submission3.ui.profile

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bintangfajarianto.submission3.databinding.ActivityProfileBinding
import com.bintangfajarianto.submission3.data.model.Profile
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

        // Set up Button
        binding.apply {
            profileButton.setOnClickListener {
                var uri = Uri.parse("http://instagram.com/_u/${myProfile.username}/")
                var igIntent = Intent(Intent.ACTION_VIEW, uri).setPackage("com.instagram.android")

                try {
                    startActivity(igIntent)
                } catch (e: ActivityNotFoundException) {
                    uri = Uri.parse("http://instagram.com/${myProfile.username}/")
                    igIntent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(igIntent)
                }
            }

            actionBarLayout.actionBarBack.setOnClickListener {
                finish()
            }
        }
    }

    private fun setProfile(profile: Profile) {
        binding.apply {
            profileName.text = profile.fullname
            profileUsername.text = profile.username
            profileCompany.text = profile.company
            profileLocation.text = profile.location
            Glide.with(applicationContext)
                .load(profile.avatar)
                .into(profileAvatar)
        }
    }

    companion object {
        const val EXTRA_PROFILE = "extra_profile"
    }
}