package com.example.megagigasolusindoapplication.Model

import com.google.gson.annotations.SerializedName

data class RequestBarang(
//	Auto Increment
//	@field:SerializedName("id")
//	val id: Int? = null,

    @field:SerializedName("namaBarang")
    val namaBarang: String? = null,

    @field:SerializedName("harga")
    val harga: Double? = null,

    @field:SerializedName("stok")
    val stok: Int? = null,

    @field:SerializedName("supplier")
    val supplier: SupplierData? = null,
)

data class SupplierData(

    @field:SerializedName("namaSupplier")
    val namaSupplier: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("noTelp")
    val noTelp: String? = null,

    @field:SerializedName("alamat")
    val alamat: String? = null
)