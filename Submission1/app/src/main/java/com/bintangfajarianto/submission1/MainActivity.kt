package com.bintangfajarianto.submission1

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.bintangfajarianto.submission1.ui.HomeActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            val intentHome = Intent(this@MainActivity, HomeActivity::class.java)
            startActivity(intentHome)
            finish()
        }, 3000)
    }
}