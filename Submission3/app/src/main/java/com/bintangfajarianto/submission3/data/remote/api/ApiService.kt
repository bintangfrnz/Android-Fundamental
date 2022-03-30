package com.bintangfajarianto.submission3.data.remote.api

import com.bintangfajarianto.submission3.BuildConfig
import com.bintangfajarianto.submission3.data.remote.response.SearchUser
import com.bintangfajarianto.submission3.data.remote.response.User
import com.bintangfajarianto.submission3.data.remote.response.UserDetail
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @Headers("Authorization: token " + BuildConfig.API_KEY)
    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String) : Call<UserDetail>

    @Headers("Authorization: token " + BuildConfig.API_KEY)
    @GET("search/users")
    fun searchUser(@Query("q") q: String) : Call<SearchUser>

    @Headers("Authorization: token " + BuildConfig.API_KEY)
    @GET("users/{username}/followers")
    fun getUserFollower(@Path("username") username: String) : Call<List<User>>

    @Headers("Authorization: token " + BuildConfig.API_KEY)
    @GET("users/{username}/following")
    fun getUserFollowing(@Path("username") username: String) : Call<List<User>>


}