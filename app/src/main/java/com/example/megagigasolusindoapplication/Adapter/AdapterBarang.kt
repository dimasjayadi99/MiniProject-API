package com.example.megagigasolusindoapplication.Adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.megagigasolusindoapplication.API.Auth.SessionManager
import com.example.megagigasolusindoapplication.API.Barang.ApiConfigBarang
import com.example.megagigasolusindoapplication.Activity.AddBarangActivity
import com.example.megagigasolusindoapplication.Model.DataItem
import com.example.megagigasolusindoapplication.Model.ResponseBarang
import com.example.megagigasolusindoapplication.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdapterBarang(val listBarang: List<DataItem?>?) :
    RecyclerView.Adapter<AdapterBarang.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val card = view.findViewById<CardView>(R.id.carditem)
        val namaBarang = view.findViewById<TextView>(R.id.tv_namaBarang)
        val hargaBarang = view.findViewById<TextView>(R.id.tv_hargaBarang)
        val stokBarang = view.findViewById<TextView>(R.id.tv_stokBarang)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_barang, parent, false)
        return MyViewHolder(view)
    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.namaBarang.text = "Nama Barang : " + listBarang?.get(position)?.namaBarang
        holder.hargaBarang.text = "Harga : " + listBarang?.get(position)?.harga.toString()
        holder.stokBarang.text = "Stok Tersedia : " + listBarang?.get(position)?.stok.toString()
        val id = listBarang?.get(position)?.id

        // card set on click
        // show alert dialog
        holder.card.setOnLongClickListener {
            // setup alert dialog
            val alertDialog = AlertDialog.Builder(it.context)
            alertDialog.setTitle("Data Barang")
            alertDialog.setMessage("Update atau Hapus Data?")
            // choice update button, run update function
            alertDialog.setPositiveButton("Update") { dialogInterface, which ->
                if (id != null) {
                    // kirim data barang ke activity update barang
                    val intent = Intent(it.context, AddBarangActivity::class.java)
                    // check update
                    intent.putExtra("aksi", "update")
                    // data barang
                    intent.putExtra("idBarang", listBarang?.get(position)?.id)
                    intent.putExtra("namaBarang", listBarang?.get(position)?.namaBarang)
                    intent.putExtra("harga", listBarang?.get(position)?.harga.toString())
                    intent.putExtra("stok", listBarang?.get(position)?.stok.toString())
                    // data supplier
                    intent.putExtra("idSupplier", listBarang?.get(position)?.supplier?.id)
                    intent.putExtra(
                        "namaSupplier",
                        listBarang?.get(position)?.supplier?.namaSupplier
                    )
                    intent.putExtra("alamatSupplier", listBarang?.get(position)?.supplier?.alamat)
                    intent.putExtra("telpSupplier", listBarang?.get(position)?.supplier?.noTelp)
                    it.context.startActivity(intent)
                }
            }
            // choice delete button, run delete function
            alertDialog.setNegativeButton("Delete") { dialogInterface, which ->
                if (id != null) {
                    ApiConfigBarang.getService()
                        .deleteBarang(id, "Bearer ${SessionManager(it.context).fetchAuthToken()}")
                        .enqueue(object : Callback<ResponseBarang> {
                            override fun onResponse(
                                call: Call<ResponseBarang>,
                                response: Response<ResponseBarang>
                            ) {
                                Toast.makeText(it.context, "Berhasil Dihapus", Toast.LENGTH_SHORT)
                                    .show()
                            }

                            override fun onFailure(call: Call<ResponseBarang>, t: Throwable) {
                                Toast.makeText(it.context, t.localizedMessage, Toast.LENGTH_SHORT)
                                    .show()
                            }
                        })
                }
            }
            //performing cancel action
            alertDialog.setNeutralButton("Cancel") { dialogInterface, which ->
                dialogInterface.dismiss()
            }
            alertDialog.setCancelable(true)
            alertDialog.create().show()

            true
        }

    }

    override fun getItemCount(): Int {
        if (listBarang != null) {
            return listBarang.size
        }
        return 0
    }


}