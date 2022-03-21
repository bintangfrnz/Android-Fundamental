package com.bintangfajarianto.submission1.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bintangfajarianto.submission1.databinding.ActivityProfileBinding
import com.bintangfajarianto.submission1.model.Profile
import com.bumptech.glide.Glide

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    companion object {
        const val EXTRA_PROFILE = "extra_profile"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val name = binding.profileName
        val username = binding.profileUsername
        val email = binding.profileEmail
        val avatar = binding.profileAvatar
        val btnIG = binding.profileButton
        val back = binding.actionBarLayout.actionBarBack

        val myProfile = intent.getParcelableExtra<Profile>(EXTRA_PROFILE) as Profile

        name.text = myProfile.fullname
        username.text = myProfile.username
        email.text = myProfile.email
        Glide.with(applicationContext)
            .load(myProfile.avatar)
            .into(avatar)
        btnIG.setOnClickListener {
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
        back.setOnClickListener {
            finish()
        }
    }
}