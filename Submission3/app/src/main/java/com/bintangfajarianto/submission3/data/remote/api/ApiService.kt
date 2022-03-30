package com.bintangfajarianto.submission3.data.remote.api

import com.bintangfajarianto.submission3.BuildConfig
import com.bintangfajarianto.submission3.data.remote.response.SearchUser
import com.bintangfajarianto.submission3.data.remote.response.User
import com.bintangfajarianto.submission3.data.remote.response.UserDetail
import retrofit2.http.*

interface ApiService {

    @Headers("Authorization: token " + BuildConfig.API_KEY)
    @GET("users/{username}")
    suspend fun getDetailUser(@Path("username") username: String) : UserDetail

    @Headers("Authorization: token " + BuildConfig.API_KEY)
    @GET("search/users")
    suspend fun searchUser(@Query("q") q: String) : SearchUser

    @Headers("Authorization: token " + BuildConfig.API_KEY)
    @GET("users/{username}/followers")
    suspend fun getUserFollower(@Path("username") username: String) : List<User>

    @Headers("Authorization: token " + BuildConfig.API_KEY)
    @GET("users/{username}/following")
    suspend fun getUserFollowing(@Path("username") username: String) : List<User>


}