package com.bintangfajarianto.submission2.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bintangfajarianto.submission2.api.ApiConfig
import com.bintangfajarianto.submission2.model.SearchUser
import com.bintangfajarianto.submission2.model.UserDetail
import com.bintangfajarianto.submission2.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {

    private val _textInput = MutableLiveData<String>()
    val textInput: LiveData<String> = _textInput

    private val _totalUser = MutableLiveData<Int>()
    val totalUser: LiveData<Int> = _totalUser

    private val _listUser = MutableLiveData<List<User>>()
    val listUser: LiveData<List<User>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun search(username: String) {
        _textInput.value = username
        getUsersByUsername(username)
    }

    private fun getUsersByUsername(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().searchUser(username)
        client.enqueue(object: Callback<SearchUser> {
            override fun onResponse(call: Call<SearchUser>, response: Response<SearchUser>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    _totalUser.value = responseBody?.totalCount
                    _listUser.value = responseBody?.users
                } else {
                    Log.e(TAG, "onFailute: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchUser>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "SearchViewModel"
    }

}