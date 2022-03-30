package com.bintangfajarianto.submission3.di

import android.content.Context
import com.bintangfajarianto.submission3.data.local.room.UserDatabase
import com.bintangfajarianto.submission3.data.remote.api.ApiConfig
import com.bintangfajarianto.submission3.ui.home.favorite.FavoriteRepository

object Injection {
    fun provideRepository(context: Context): FavoriteRepository {
        val apiService = ApiConfig.getApiService()
        val database = UserDatabase.getInstance(context)
        val dao = database.userDao()
        return FavoriteRepository.getInstance(apiService, dao)
    }
}