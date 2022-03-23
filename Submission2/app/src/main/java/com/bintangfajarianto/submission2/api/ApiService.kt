package com.bintangfajarianto.submission2.api

import com.bintangfajarianto.submission2.model.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @Headers("Authorization: token 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")

    @GET("search/users")
    fun searchUser(@Query("q") q: String) : Call<SearchUser>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String) : Call<User>

    @GET("users/{username}/followers")
    fun getUserFollower(@Path("username") username: String) : Call<UserConnection>

    @GET("users/{username}/following")
    fun getUserFollowing(@Path("username") username: String) : Call<UserConnection>


}