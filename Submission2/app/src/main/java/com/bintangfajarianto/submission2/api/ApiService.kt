package com.bintangfajarianto.submission2.api

import com.bintangfajarianto.submission2.model.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

//    @Headers("Authorization: token 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")
//    @Headers("Authorization: token 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")

    @Headers("Authorization: token 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")
    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String) : Call<UserDetail>

    @Headers("Authorization: token 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")
    @GET("search/users")
    fun searchUser(@Query("q") q: String) : Call<SearchUser>

    @Headers("Authorization: token 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")
    @GET("users/{username}/followers")
    fun getUserFollower(@Path("username") username: String) : Call<List<User>>

    @Headers("Authorization: token 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")
    @GET("users/{username}/following")
    fun getUserFollowing(@Path("username") username: String) : Call<List<User>>


}