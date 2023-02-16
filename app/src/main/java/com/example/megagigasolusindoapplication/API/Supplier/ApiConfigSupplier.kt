package com.example.megagigasolusindoapplication.API.Supplier

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfigSupplier {

    const val base_url = "http://159.223.57.121:8090/supplier/"

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getService(): ApiServiceSupplier {
        return getRetrofit().create(ApiServiceSupplier::class.java)
    }

}