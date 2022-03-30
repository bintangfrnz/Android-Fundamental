package com.bintangfajarianto.submission3.ui.home.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bintangfajarianto.submission3.data.remote.api.ApiConfig
import com.bintangfajarianto.submission3.data.remote.response.SearchUser
import com.bintangfajarianto.submission3.data.remote.response.User
import com.bintangfajarianto.submission3.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {

    private val _messageError = MutableLiveData<String>()
    val messageError: LiveData<String> = _messageError

    private val _listUser = MutableLiveData<List<User>>()
    val listUser: LiveData<List<User>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUsersByUsername(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().searchUser(username)
        client.enqueue(object: Callback<SearchUser> {
            override fun onResponse(call: Call<SearchUser>, response: Response<SearchUser>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    _listUser.value = responseBody?.users
                    _messageError.value = Constants.BLANK

                    if (responseBody?.totalCount == 0)
                        _messageError.value = "$username tidak ditemukan"
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

    fun reset() {
        _listUser.value = arrayListOf()
        _messageError.value = "No Input"
    }

    companion object {
        private const val TAG = "SearchViewModel"
    }

}