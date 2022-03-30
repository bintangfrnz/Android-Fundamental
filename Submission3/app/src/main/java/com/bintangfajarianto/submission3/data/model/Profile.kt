package com.bintangfajarianto.submission3.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Profile(
    val fullname: String?,
    val username: String?,
    val company: String?,
    val location: String?,
    val avatar: String?
) : Parcelable