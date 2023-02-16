package com.example.megagigasolusindoapplication.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.megagigasolusindoapplication.API.Auth.ApiConfigAuth
import com.example.megagigasolusindoapplication.API.Auth.SessionManager
import com.example.megagigasolusindoapplication.Model.Response.ResponseUser
import com.example.megagigasolusindoapplication.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    // init variable edit text and button
    lateinit var et_profileName: EditText
    lateinit var et_username: EditText
    lateinit var et_password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        // desc with find by id
        et_profileName = findViewById(R.id.et_profilename)
        et_username = findViewById(R.id.et_username)
        et_password = findViewById(R.id.et_password)

        val button = findViewById<Button>(R.id.btn_register)
        // if button register on click
        button.setOnClickListener {
            // validation blank field
            val profileName = et_profileName.text.toString()
            val username = et_username.text.toString()
            val password = et_password.text.toString()

            // empty field
            if (profileName.isEmpty()) {
                et_profileName.error = "Empty Field"
                et_profileName.requestFocus()
            } else if (username.isEmpty()) {
                et_username.error = "Empty Field"
                et_username.requestFocus()
            } else if (password.isEmpty()) {
                et_password.error = "Empty Field"
                et_password.requestFocus()
            }
            // not empty
            else {
                // set request body
                val request = ResponseUser(profileName, username, password)
                // send request function
                ApiConfigAuth.getService()
                    .registerUser(request, "Bearer ${SessionManager(this).fetchAuthToken()}")
                    .enqueue(object : Callback<ResponseUser> {
                        override fun onResponse(
                            call: Call<ResponseUser>,
                            response: Response<ResponseUser>
                        ) {
                            // intent to Login page if success
                            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                            Toast.makeText(
                                this@RegisterActivity,
                                "Berhasil Membuat Akun",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                            // show error message
                            Toast.makeText(
                                this@RegisterActivity,
                                t.localizedMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    })
            }

        }
    }

    // intent to login activity
    fun gotoLogin(view: View) {
        val intent = Intent(view.context, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}