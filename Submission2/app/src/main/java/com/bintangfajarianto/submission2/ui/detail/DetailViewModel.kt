package com.bintangfajarianto.submission2.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bintangfajarianto.submission2.api.ApiConfig
import com.bintangfajarianto.submission2.model.User
import com.bintangfajarianto.submission2.model.UserConnection
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    lateinit var url: String

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _follower = MutableLiveData<List<User>>()
    val follower: LiveData<List<User>> = _follower

    private val _isLoading1 = MutableLiveData<Boolean>()
    val isLoading1: LiveData<Boolean> = _isLoading1

    private val _isLoading2 = MutableLiveData<Boolean>()
    val isLoading2: LiveData<Boolean> = _isLoading2

    fun findUser(username: String) {
        _isLoading1.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object: Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                _isLoading1.value = false

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _user.value = responseBody
                        url = responseBody.htmlUrl
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                _isLoading1.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun findFollower(username: String) {
        _isLoading2.value = true
        val client = ApiConfig.getApiService().getUserFollower(username)
        client.enqueue(object: Callback<UserConnection> {
            override fun onResponse(call: Call<UserConnection>, response: Response<UserConnection>) {
                _isLoading2.value = false

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val listUserItem = responseBody.userConnection
                        var list: List<User> = arrayListOf()

                        for (user in listUserItem) {

                        }
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserConnection>, t: Throwable) {
                _isLoading2.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }
}