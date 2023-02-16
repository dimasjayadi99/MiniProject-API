package com.example.megagigasolusindoapplication.API.Supplier

import com.example.megagigasolusindoapplication.Model.Request.RequestSupplier
import com.example.megagigasolusindoapplication.Model.ResponseSupplier
import retrofit2.Call
import retrofit2.http.*

interface ApiServiceSupplier {

    @GET("find-all")
    fun getAllSupplier(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Header("Authorization") token: String
    ): Call<ResponseSupplier>

    @POST("create")
    fun createSupplier(
        @Body body: RequestSupplier,
        @Header("Authorization") token: String
    ): Call<RequestSupplier>

    @DELETE("delete/{id}")
    fun deleteSupplier(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): Call<RequestSupplier>

    @PUT("update/{id}")
    fun updateBarang(
        @Path("id") id: Int,
        @Body body: RequestSupplier,
        @Header("Authorization") token: String
    ): Call<RequestSupplier>

}