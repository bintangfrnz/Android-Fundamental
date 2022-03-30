package com.bintangfajarianto.submission3.ui.home.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bintangfajarianto.submission3.data.local.entity.UserEntity
import kotlinx.coroutines.launch

class FavoriteViewModel(private val favoriteRepository: FavoriteRepository) : ViewModel() {

    fun getFavoriteUsers() : LiveData<List<UserEntity>> = favoriteRepository.getFavoriteUsers()

    fun isUserFavorite(username: String): Boolean = favoriteRepository.isUserFavorite(username)

    fun addToFavorites(user: UserEntity) {
        viewModelScope.launch {
            favoriteRepository.addToFavorites(user)
        }
    }
    fun removeFromFavorites(username: String) {
        viewModelScope.launch {
            favoriteRepository.removeFromFavorites(username)
        }
    }
    fun removeAll() {
        viewModelScope.launch {
            favoriteRepository.removeAll()
        }
    }
}