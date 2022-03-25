package com.bintangfajarianto.submission2.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bintangfajarianto.submission2.api.ApiConfig
import com.bintangfajarianto.submission2.model.UserDetail
import com.bintangfajarianto.submission2.model.User
import com.bintangfajarianto.submission2.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    lateinit var url: String

    private val _user = MutableLiveData<UserDetail>()
    val user: LiveData<UserDetail> = _user

    private val _listFollow = MutableLiveData<List<User>>()
    val listFollow: LiveData<List<User>> = _listFollow

    private val _isLoadingData = MutableLiveData<Boolean>()
    val isLoadingData: LiveData<Boolean> = _isLoadingData

    private val _isLoadingFragment = MutableLiveData<Boolean>()
    val isLoadingFragment: LiveData<Boolean> = _isLoadingFragment

    private val _messageError = MutableLiveData<String>()
    val messageError: LiveData<String> = _messageError

    private val _currentTab = MutableLiveData(0)
    val currentTab: LiveData<Int> = _currentTab

    fun findUser(username: String) {
        _isLoadingData.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object: Callback<UserDetail> {
            override fun onResponse(call: Call<UserDetail>, response: Response<UserDetail>) {
                _isLoadingData.value = false

                if (response.isSuccessful) {
                    Log.i(TAG, "FIND MY PROFILE")
                    val responseBody = response.body()
                    _user.value = responseBody!!
                    url = responseBody.htmlUrl
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserDetail>, t: Throwable) {
                _isLoadingData.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun findFollower(username: String) {
        _isLoadingFragment.value = true
        val client = ApiConfig.getApiService().getUserFollower(username)
        client.enqueue(object: Callback<List<User>> {

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                _isLoadingFragment.value = false
                if (response.isSuccessful) {
                    Log.i(TAG, "FIND MY FOLLOWER")

                    val responseBody = response.body()
                    _listFollow.value = responseBody!!

                    if (responseBody.isNullOrEmpty())
                        _messageError.value = "0 Follower"
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                _isLoadingFragment.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun findFollowing(username: String) {
        _isLoadingFragment.value = true
        val client = ApiConfig.getApiService().getUserFollowing(username)
        client.enqueue(object: Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                _isLoadingFragment.value = false

                if (response.isSuccessful) {
                    Log.i(TAG, "FIND MY FOLLOWING")

                    val responseBody = response.body()
                    _listFollow.value = responseBody!!

                    if (responseBody.isNullOrEmpty())
                        _messageError.value = "0 Following"
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                _isLoadingFragment.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun setTab(tab: Int) {
        _currentTab.value = tab
    }

    fun resetList() {
        _listFollow.value = arrayListOf()
        _messageError.value = Constants.BLANK
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }
}