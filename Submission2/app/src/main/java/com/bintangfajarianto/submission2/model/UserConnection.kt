package com.bintangfajarianto.submission2.model

import com.google.gson.annotations.SerializedName

data class UserConnection(

	@field:SerializedName("UserConnection")
	val userConnection: List<UserItem>
)
