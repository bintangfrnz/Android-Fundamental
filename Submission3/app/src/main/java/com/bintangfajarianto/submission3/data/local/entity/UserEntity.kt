package com.bintangfajarianto.submission3.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
class UserEntity(
    @field:ColumnInfo(name = "username")
    @field:PrimaryKey
    val username: String,

    @field:ColumnInfo(name = "avatarUrl")
    val avatarUrl: String,

    @field:ColumnInfo(name = "favorite")
    var isFavorite: Boolean
)