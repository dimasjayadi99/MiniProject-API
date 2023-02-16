package com.example.megagigasolusindoapplication.API.Barang

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfigBarang {
    const val base_url = "http://159.223.57.121:8090/barang/"

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getService(): ApiServiceBarang {
        return getRetrofit().create(ApiServiceBarang::class.java)
    }

}