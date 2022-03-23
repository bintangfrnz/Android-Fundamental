package com.bintangfajarianto.submission2.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Profile(
    var fullname: String?,
    var username: String?,
    var company: String?,
    var location: String?,
    var avatar: Int?
) : Parcelable