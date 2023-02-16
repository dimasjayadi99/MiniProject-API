package com.example.megagigasolusindoapplication.Fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.megagigasolusindoapplication.API.Auth.SessionManager
import com.example.megagigasolusindoapplication.API.Supplier.ApiConfigSupplier
import com.example.megagigasolusindoapplication.Activity.AddSupplier
import com.example.megagigasolusindoapplication.Activity.LoginActivity
import com.example.megagigasolusindoapplication.Activity.PreInputBarang
import com.example.megagigasolusindoapplication.Adapter.AdapterBarang
import com.example.megagigasolusindoapplication.Adapter.AdapterSupplier
import com.example.megagigasolusindoapplication.Model.DataItemSupplier
import com.example.megagigasolusindoapplication.Model.ResponseSupplier
import com.example.megagigasolusindoapplication.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SupplierFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = LayoutInflater.from(container?.context)
            .inflate(R.layout.fragment_supplier, container, false)

        val rv_supplier = view.findViewById<RecyclerView>(R.id.rv_supplier)

        val fab = view.findViewById<FloatingActionButton>(R.id.fab)

        fab.setOnClickListener {
            val intent = Intent(it.context, AddSupplier::class.java)
            startActivity(intent)
        }

        ApiConfigSupplier.getService().getAllSupplier(10, 1, "Bearer ${this.context?.let { SessionManager(it).fetchAuthToken() }}"
        ).enqueue(object : Callback<ResponseSupplier> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<ResponseSupplier>,
                response: Response<ResponseSupplier>
            ) {
                if (response.isSuccessful) {
                    val responsebody = response.body()
                    val responselist = responsebody?.data
                    val adapterSupplier = AdapterSupplier(responselist)
                    rv_supplier.apply {
                        layoutManager = LinearLayoutManager(view.context)
                        setHasFixedSize(true)
                        adapterSupplier.notifyDataSetChanged()
                        adapter = adapterSupplier
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

            override fun onFailure(call: Call<ResponseSupplier>, t: Throwable) {
                Toast.makeText(view.context, t.message, Toast.LENGTH_SHORT).show()
            }

        })

        return view
    }

}