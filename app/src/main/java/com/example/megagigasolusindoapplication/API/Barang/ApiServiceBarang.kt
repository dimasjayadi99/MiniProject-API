package com.example.megagigasolusindoapplication.API.Barang

import com.example.megagigasolusindoapplication.Model.RequestBarang
import com.example.megagigasolusindoapplication.Model.ResponseBarang
import retrofit2.Call
import retrofit2.http.*

interface ApiServiceBarang {
    @GET("find-all")
    fun getAllBarang(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Header("Authorization") token: String
    ): Call<ResponseBarang>

    @POST("create")
    fun createBarang(
        @Body body: RequestBarang, @Header("Authorization") token: String
    ): Call<RequestBarang>

    @DELETE("delete/{id}")
    fun deleteBarang(
        @Path("id") id: Int, @Header("Authorization") token: String
    ): Call<ResponseBarang>

    @PUT("update/{id}")
    fun updateBarang(
        @Path("id") id: Int, @Body body: RequestBarang, @Header("Authorization") token: String
    ): Call<RequestBarang>

}