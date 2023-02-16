package com.example.megagigasolusindoapplication.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.megagigasolusindoapplication.API.Auth.SessionManager
import com.example.megagigasolusindoapplication.API.Supplier.ApiConfigSupplier
import com.example.megagigasolusindoapplication.Model.Request.RequestSupplier
import com.example.megagigasolusindoapplication.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddSupplier : AppCompatActivity() {


    // init
    lateinit var et_namaSupplier: EditText
    lateinit var et_alamat: EditText
    lateinit var et_telp: EditText
    lateinit var button: Button

    // variable to save intent data from adapter
    var aksi: String = ""
    lateinit var getNamaSupplier: String
    lateinit var getAlamat: String
    lateinit var getTelp: String
    var idSupplier: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_supplier)


        // desc
        button = findViewById(R.id.btn_addSupplier)
        et_namaSupplier = findViewById(R.id.et_namaSupplier)
        et_alamat = findViewById(R.id.et_alamat)
        et_telp = findViewById(R.id.et_telp)

        // get intent data
        aksi = intent.getStringExtra("aksi").toString()
        idSupplier = intent.getIntExtra("idSupplier", 0)
        getNamaSupplier = intent.getStringExtra("namaSupplier").toString()
        getAlamat = intent.getStringExtra("alamatSupplier").toString()
        getTelp = intent.getStringExtra("telpSupplier").toString()

        // check for aksi value
        // if value is update run update function
        // if value is null run create function
        if (aksi.equals("update")) {
            // set text
            et_namaSupplier.setText(getNamaSupplier)
            et_alamat.setText(getAlamat)
            et_telp.setText(getTelp)
            // call function
            updateSupplier()
        } else {
            //call function
            createSupplier()
        }

    }

    // function create supplier
    private fun createSupplier() {
        button.setOnClickListener {
            // validate
            val namaSupplier = et_namaSupplier.text.toString()
            val alamat = et_alamat.text.toString()
            val telp = et_telp.text.toString()

            if (namaSupplier.isEmpty()) {
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
                val request = RequestSupplier(namaSupplier, alamat, telp)
                // call api service
                ApiConfigSupplier.getService().createSupplier(
                    request,
                    "Bearer ${SessionManager(this).fetchAuthToken()}"
                ).enqueue(object : Callback<RequestSupplier> {
                    override fun onResponse(
                        call: Call<RequestSupplier>,
                        response: Response<RequestSupplier>
                    ) {
                        // get response body
                        val intent = Intent(this@AddSupplier, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                        Toast.makeText(this@AddSupplier, "Berhasil Simpan Data", Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onFailure(call: Call<RequestSupplier>, t: Throwable) {
                        // failure message
                        Toast.makeText(this@AddSupplier, t.localizedMessage, Toast.LENGTH_SHORT)
                            .show()
                    }

                })
            }
        }
    }

    // update function
    private fun updateSupplier() {
        button.setOnClickListener {
            // validate
            val namaSupplier = et_namaSupplier.text.toString()
            val alamat = et_alamat.text.toString()
            val telp = et_telp.text.toString()
            if (namaSupplier.isEmpty()) {
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
                val request = RequestSupplier(namaSupplier, alamat, telp)
                // function
                ApiConfigSupplier.getService().updateBarang(
                    idSupplier,
                    request,
                    "Bearer ${SessionManager(this).fetchAuthToken()}"
                ).enqueue(object : Callback<RequestSupplier> {
                    override fun onResponse(
                        call: Call<RequestSupplier>,
                        response: Response<RequestSupplier>
                    ) {
                        val intent = Intent(this@AddSupplier, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                        Toast.makeText(this@AddSupplier, "Berhasil Update Data", Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onFailure(call: Call<RequestSupplier>, t: Throwable) {
                        Toast.makeText(this@AddSupplier, t.localizedMessage, Toast.LENGTH_SHORT)
                            .show()
                    }

                })
            }
        }
    }
}