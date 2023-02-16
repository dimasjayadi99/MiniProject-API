package com.example.megagigasolusindoapplication.API.Auth

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfigAuth {

    const val base_url = "http://159.223.57.121:8090/auth/"

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getService(): ApiServiceAuth {
        return getRetrofit().create(ApiServiceAuth::class.java)
    }

}