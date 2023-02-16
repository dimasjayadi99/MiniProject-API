package com.example.megagigasolusindoapplication.Model.Request

import com.google.gson.annotations.SerializedName

data class RequestLogin(

    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("password")
    val password: String? = null

)
