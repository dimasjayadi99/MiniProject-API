package com.example.megagigasolusindoapplication.Model.Request

import com.google.gson.annotations.SerializedName

data class RequestSupplier(

//	@field:SerializedName("id")
//	val id: Int? = null,

    @field:SerializedName("namaSupplier")
    val namaSupplier: String? = null,

    @field:SerializedName("alamat")
    val alamat: String? = null,

    @field:SerializedName("noTelp")
    val noTelp: String? = null

)
