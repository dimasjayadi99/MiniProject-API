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
import com.example.megagigasolusindoapplication.Model.Request.RequestLogin
import com.example.megagigasolusindoapplication.Model.ResponseLogin
import com.example.megagigasolusindoapplication.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    // init variable edit text and button
    lateinit var et_username: EditText
    lateinit var et_password: EditText
    lateinit var btn_login: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // desc with find by id
        et_username = findViewById(R.id.et_username)
        et_password = findViewById(R.id.et_password)

        btn_login = findViewById(R.id.btn_login)

        // if button login on click
        btn_login.setOnClickListener {
            // validation blank field
            val username = et_username.text.toString()
            val password = et_password.text.toString()
            if (username.isEmpty()) {
                et_username.error = "Empty Field"
                et_username.requestFocus()
            } else if (password.isEmpty()) {
                et_password.error = "Empty Field"
                et_password.requestFocus()
            } else {
                // login function
                // call session manager to safe data login (token)
                val sessionManager = SessionManager(this)

                // field requirement for login by request RequestLogin Class
                val request = RequestLogin(username, password)
                // Callback
                ApiConfigAuth.getService().loignUser(request)
                    .enqueue(object : Callback<ResponseLogin> {
                        override fun onResponse(
                            call: Call<ResponseLogin>,
                            response: Response<ResponseLogin>
                        ) {
                            // get response
                            val loginResponse = response.body()
                            val status = response.body()?.message
                            // if failed login
                            if (status.equals("LOGIN FAILED")) {
                                Toast.makeText(
                                    this@LoginActivity,
                                    response.body()?.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            // or not
                            else if (status.equals("LOGIN SUCCESS")) {
                                // save token for auth
                                sessionManager.saveAuthToken(loginResponse?.data?.token.toString())
                                // intent to Main activity (Homepage)
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                                Toast.makeText(
                                    this@LoginActivity,
                                    response.body()?.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                            Toast.makeText(
                                this@LoginActivity,
                                t.localizedMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    })
            }
        }

    }

    // Register Direct text
    fun gotoRegister(view: View) {
        val intent = Intent(view.context, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }
}