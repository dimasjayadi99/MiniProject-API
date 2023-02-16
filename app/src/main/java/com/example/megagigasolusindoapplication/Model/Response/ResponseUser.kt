package com.example.megagigasolusindoapplication.Model.Response

import com.google.gson.annotations.SerializedName

data class ResponseUser(

    @field:SerializedName("profileName")
    val profileName: String? = null,

    @field:SerializedName("password")
    val password: String? = null,

    @field:SerializedName("username")
    val username: String? = null
)
