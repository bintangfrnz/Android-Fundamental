package com.bintangfajarianto.submission2.model

import com.google.gson.annotations.SerializedName

data class UserItem(

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("avatar_url")
    val avatarUrl: String
)
