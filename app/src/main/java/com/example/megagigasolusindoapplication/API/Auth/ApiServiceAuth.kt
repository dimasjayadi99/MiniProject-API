package com.example.megagigasolusindoapplication.API.Auth

import com.example.megagigasolusindoapplication.Model.Request.RequestLogin
import com.example.megagigasolusindoapplication.Model.Response.ResponseUser
import com.example.megagigasolusindoapplication.Model.ResponseLogin
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiServiceAuth {

    @POST("login")
    fun loignUser(@Body body: RequestLogin): Call<ResponseLogin>

    @POST("register")
    fun registerUser(
        @Body body: ResponseUser,
        @Header("Authorization") token: String
    ): Call<ResponseUser>

}