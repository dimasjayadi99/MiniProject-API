package com.example.megagigasolusindoapplication.Fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.megagigasolusindoapplication.API.Auth.SessionManager
import com.example.megagigasolusindoapplication.API.Barang.ApiConfigBarang
import com.example.megagigasolusindoapplication.Activity.LoginActivity
import com.example.megagigasolusindoapplication.Activity.PreInputBarang
import com.example.megagigasolusindoapplication.Adapter.AdapterBarang
import com.example.megagigasolusindoapplication.Model.ResponseBarang
import com.example.megagigasolusindoapplication.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BarangFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = LayoutInflater.from(container?.context)
            .inflate(R.layout.fragment_barang, container, false)

        val rv_barang = view.findViewById<RecyclerView>(R.id.rv_barang)
        val fab = view.findViewById<FloatingActionButton>(R.id.fab)

        fab.setOnClickListener {
            val intent = Intent(it.context, PreInputBarang::class.java)
            startActivity(intent)
        }

        ApiConfigBarang.getService().getAllBarang(10, 1, "Bearer ${this.context?.let { SessionManager(it).fetchAuthToken() }}").enqueue(object : Callback<ResponseBarang> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<ResponseBarang>,
                response: Response<ResponseBarang>
            ) {
                if (response.isSuccessful) {
                    val responsebody = response.body()
                    val responselist = responsebody?.data
                    val adapterBarang = AdapterBarang(responselist)
                    rv_barang.apply {
                        layoutManager = LinearLayoutManager(context)
                        setHasFixedSize(true)
                        adapterBarang.notifyDataSetChanged()
                        adapter = adapterBarang
                    }
                } else {
                    val intent = Intent(view.context, LoginActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                    Toast.makeText(
                        view.context,
                        "Sesi habis, silahkan login kembali",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseBarang>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }

        })

        return view
    }

}