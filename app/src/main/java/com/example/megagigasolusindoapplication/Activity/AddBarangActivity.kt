package com.example.megagigasolusindoapplication.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.megagigasolusindoapplication.API.Auth.SessionManager
import com.example.megagigasolusindoapplication.API.Barang.ApiConfigBarang
import com.example.megagigasolusindoapplication.Model.RequestBarang
import com.example.megagigasolusindoapplication.Model.SupplierData
import com.example.megagigasolusindoapplication.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddBarangActivity : AppCompatActivity() {

    // init edit text button, etc
    lateinit var et_namaBarang: EditText
    lateinit var et_harga: EditText
    lateinit var et_stok: EditText
    lateinit var et_namaSupplier: EditText
    lateinit var et_alamat: EditText
    lateinit var et_telp: EditText
    lateinit var button: Button

    // variable to save intent data

    lateinit var getNamaSupplier: String
    lateinit var getAlamatSupplier: String
    lateinit var getTelpSupplier: String

    var getIdBarang: Int = 0
    lateinit var getNamaBarang: String
    lateinit var getHargaBarang: String
    lateinit var getStokBarang: String
    var idSupplier: Int = 0
    lateinit var aksi: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_barang)

        // description
        button = findViewById(R.id.btn_addBarang)

        et_namaBarang = findViewById(R.id.et_namaBarang)
        et_harga = findViewById(R.id.et_harga)
        et_stok = findViewById(R.id.et_stok)

        et_namaSupplier = findViewById(R.id.et_namaSupplier)
        et_alamat = findViewById(R.id.et_alamat)
        et_telp = findViewById(R.id.et_telp)

        // get intent data from adapter
        aksi = intent.getStringExtra("aksi").toString()
        idSupplier = intent.getIntExtra("idSupplier", 0)
        getNamaSupplier = intent.getStringExtra("namaSupplier").toString()
        getAlamatSupplier = intent.getStringExtra("alamatSupplier").toString()
        getTelpSupplier = intent.getStringExtra("telpSupplier").toString()

        getIdBarang = intent.getIntExtra("idBarang", 0)
        getNamaBarang = intent.getStringExtra("namaBarang").toString()
        getHargaBarang = intent.getStringExtra("harga").toString()
        getStokBarang = intent.getStringExtra("stok").toString()

        et_namaSupplier.setText(getNamaSupplier)
        et_alamat.setText(getAlamatSupplier)
        et_telp.setText(getTelpSupplier)

        // check for aksi value
        // if value is update run update function
        // if value is null run create function
        if (aksi.equals("create")) {
            // call function
            createBarang()
        } else {
            // set text
            et_namaBarang.setText(getNamaBarang)
            et_harga.setText(getHargaBarang)
            et_stok.setText(getStokBarang)
            // call function
            updateBarang()
        }
    }

    // function update
    private fun updateBarang() {
        button.setOnClickListener {
            // validation
            val namaBarang = et_namaBarang.text.toString()
            val harga = et_harga.text.toString()
            val stok = et_stok.text.toString()
            val namaSupplier = et_namaSupplier.text.toString()
            val alamat = et_alamat.text.toString()
            val telp = et_telp.text.toString()

            if (namaBarang.isEmpty()) {
                et_namaBarang.error = "Empty Field"
                et_namaBarang.requestFocus()
            } else if (harga.isEmpty()) {
                et_harga.error = "Empty Field"
                et_harga.requestFocus()
            } else if (stok.isEmpty()) {
                et_stok.error = "Empty Field"
                et_stok.requestFocus()
            } else if (namaSupplier.isEmpty()) {
                et_namaSupplier.error = "Empty Field"
                et_namaSupplier.requestFocus()
            } else if (alamat.isEmpty()) {
                et_alamat.error = "Empty Field"
                et_alamat.requestFocus()
            } else if (telp.isEmpty()) {
                et_telp.error = "Empty Field"
                et_telp.requestFocus()
            } else {
                // set request body
                val request = RequestBarang(
                    namaBarang, harga.toDouble(), stok.toInt(),
                    SupplierData(namaSupplier, idSupplier, alamat, telp)
                )
                // function
                ApiConfigBarang.getService().updateBarang(
                    getIdBarang,
                    request,
                    "Bearer ${SessionManager(this).fetchAuthToken()}"
                ).enqueue(object : Callback<RequestBarang> {
                    override fun onResponse(
                        call: Call<RequestBarang>,
                        response: Response<RequestBarang>
                    ) {
                        val intent = Intent(this@AddBarangActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                        Toast.makeText(
                            this@AddBarangActivity,
                            "Berhasil Update Data",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onFailure(call: Call<RequestBarang>, t: Throwable) {
                        Toast.makeText(
                            this@AddBarangActivity,
                            t.localizedMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                })
            }
        }
    }

    // function create
    private fun createBarang() {
        button.setOnClickListener {
            val namaBarang = et_namaBarang.text.toString()
            val harga = et_harga.text.toString()
            val stok = et_stok.text.toString()

            val namaSupplier = et_namaSupplier.text.toString()
            val alamat = et_alamat.text.toString()
            val telp = et_telp.text.toString()

            if (namaBarang.isEmpty()) {
                et_namaBarang.error = "Empty Field"
                et_namaBarang.requestFocus()
            } else if (harga.isEmpty()) {
                et_harga.error = "Empty Field"
                et_harga.requestFocus()
            } else if (stok.isEmpty()) {
                et_stok.error = "Empty Field"
                et_stok.requestFocus()
            } else if (namaSupplier.isEmpty()) {
                et_namaSupplier.error = "Empty Field"
                et_namaSupplier.requestFocus()
            } else if (alamat.isEmpty()) {
                et_alamat.error = "Empty Field"
                et_alamat.requestFocus()
            } else if (telp.isEmpty()) {
                et_telp.error = "Empty Field"
                et_telp.requestFocus()
            } else {
                // set request body
                val request = RequestBarang(
                    namaBarang, harga.toDouble(), stok.toInt(),
                    SupplierData(namaSupplier, idSupplier, alamat, telp)
                )
                // function
                ApiConfigBarang.getService()
                    .createBarang(request, "Bearer ${SessionManager(this).fetchAuthToken()}")
                    .enqueue(object : Callback<RequestBarang> {
                        override fun onResponse(
                            call: Call<RequestBarang>,
                            response: Response<RequestBarang>
                        ) {
                            val intent = Intent(this@AddBarangActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                            Toast.makeText(
                                this@AddBarangActivity,
                                "Berhasil Disimpan",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        override fun onFailure(call: Call<RequestBarang>, t: Throwable) {
                            Toast.makeText(
                                this@AddBarangActivity,
                                t.localizedMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    })
            }
        }
    }
}