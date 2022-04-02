package com.bintangfajarianto.submission3

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bintangfajarianto.submission3.ui.home.HomeActivity
import com.bintangfajarianto.submission3.ui.setting.SettingPreferences
import com.bintangfajarianto.submission3.ui.setting.SettingViewModel
import com.bintangfajarianto.submission3.ui.setting.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        val pref = SettingPreferences.getInstance(dataStore)

        val viewModel = ViewModelProvider(this, ViewModelFactory(pref))[SettingViewModel::class.java]

        viewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({
            val intentHome = Intent(this@MainActivity, HomeActivity::class.java)
            startActivity(intentHome)
            finish()
        }, SPLASH_DELAY)
    }

    companion object {
        const val SPLASH_DELAY: Long = 3000
    }
}