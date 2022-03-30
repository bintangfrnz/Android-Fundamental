package com.bintangfajarianto.submission3.ui.home.favorite

import android.util.Log
import androidx.lifecycle.*
import com.bintangfajarianto.submission3.data.local.entity.UserEntity
import com.bintangfajarianto.submission3.data.local.room.UserDao
import com.bintangfajarianto.submission3.data.remote.api.ApiService

class FavoriteRepository private constructor(
    private val apiService: ApiService,
    private val userDao: UserDao
) {
    fun getFavoriteUsers(): LiveData<List<UserEntity>> {
        Log.i("FavRepository", "Get Favorite")
        return userDao.getFavoriteUsers()
    }
    fun isUserFavorite(username: String): Boolean {
        Log.i("FavRepository", "Is $username exist?")
        return userDao.isUserFavorite(username)
    }
    suspend fun addToFavorites(user: UserEntity) {
        Log.i("FavRepository", "Add ${user.username}")
        userDao.addToFavorites(user)
    }
    suspend fun removeFromFavorites(username: String) {
        Log.i("FavRepository", "Remove $username")
        userDao.removeFromFavorites(username)
    }
    suspend fun removeAll() {
        Log.i("FavRepository", "Remove ALL")
        userDao.removeAll()
    }

    companion object {
        @Volatile
        private var instance: FavoriteRepository? = null
        fun getInstance(
            apiService: ApiService,
            userDao: UserDao
        ): FavoriteRepository =
            instance ?: synchronized(this) {
                instance ?: FavoriteRepository(apiService, userDao)
            }.also { instance = it }
    }
}