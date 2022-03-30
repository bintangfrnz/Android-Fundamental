package com.bintangfajarianto.submission3.ui.home.favorite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bintangfajarianto.submission3.data.local.entity.UserEntity
import com.bintangfajarianto.submission3.data.remote.response.User
import com.bintangfajarianto.submission3.utils.Constants
import kotlinx.coroutines.launch

class FavoriteViewModel(private val favoriteRepository: FavoriteRepository) : ViewModel() {

    private val _messageError = MutableLiveData<String>()
    val messageError: LiveData<String> = _messageError

    fun getFavoriteUsers() : LiveData<List<UserEntity>> {
        val listUser = favoriteRepository.getFavoriteUsers()
        if (listUser.value.isNullOrEmpty()) {
            Log.e("getUser", "No")
            _messageError.value = "No Favorite"
        } else {
            Log.e("getUser", "Blank")
            _messageError.value = Constants.BLANK
        }

        return listUser
    }

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