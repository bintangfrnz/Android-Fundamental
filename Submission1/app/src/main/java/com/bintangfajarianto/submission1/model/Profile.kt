package com.bintangfajarianto.submission1.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Profile(
    var fullname: String?,
    var username: String?,
    var email: String?,
    var avatar: Int?
) : Parcelable