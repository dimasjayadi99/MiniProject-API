package com.example.megagigasolusindoapplication.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.megagigasolusindoapplication.API.Auth.SessionManager
import com.example.megagigasolusindoapplication.API.Supplier.ApiConfigSupplier
import com.example.megagigasolusindoapplication.Adapter.AdapterSupplier
import com.example.megagigasolusindoapplication.Model.ResponseSupplier
import com.example.megagigasolusindoapplication.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PreInputBarang : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pre_input_barang)

        // desc and init recycler view
        val rv_supplier = findViewById<RecyclerView>(R.id.rv_supplier)
        // get data from api
        ApiConfigSupplier.getService()
            .getAllSupplier(10, 1, "Bearer ${SessionManager(this).fetchAuthToken()}")
            .enqueue(object :
                Callback<ResponseSupplier> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(
                    call: Call<ResponseSupplier>,
                    response: Response<ResponseSupplier>
                ) {
                    if (response.isSuccessful) {
                        val responsebody = response.body()
                        val responselist = responsebody?.data
                        val adapterSupplier = AdapterSupplier(responselist)
                        // set rv
                        rv_supplier.apply {
                            layoutManager = LinearLayoutManager(this@PreInputBarang)
                            setHasFixedSize(true)
                            adapterSupplier.notifyDataSetChanged()
                            adapter = adapterSupplier
                        }
                    } else {
                        val intent = Intent(this@PreInputBarang,LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                        Toast.makeText(
                            this@PreInputBarang,
                            "Sesi habis, silahkan login kembali",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ResponseSupplier>, t: Throwable) {
                    Toast.makeText(this@PreInputBarang, t.message, Toast.LENGTH_SHORT).show()
                }

            })

    }
}