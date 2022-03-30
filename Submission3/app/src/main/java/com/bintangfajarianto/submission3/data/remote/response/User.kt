package com.bintangfajarianto.submission3.data.remote.response

import com.google.gson.annotations.SerializedName

data class User(

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("avatar_url")
    val avatarUrl: String
)
