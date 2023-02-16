package com.example.megagigasolusindoapplication.Adapter

import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.megagigasolusindoapplication.API.Auth.SessionManager
import com.example.megagigasolusindoapplication.API.Supplier.ApiConfigSupplier
import com.example.megagigasolusindoapplication.Activity.AddBarangActivity
import com.example.megagigasolusindoapplication.Activity.AddSupplier
import com.example.megagigasolusindoapplication.Model.DataItemSupplier
import com.example.megagigasolusindoapplication.Model.Request.RequestSupplier
import com.example.megagigasolusindoapplication.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdapterSupplier(val listSupplier: List<DataItemSupplier?>?) :
    RecyclerView.Adapter<AdapterSupplier.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val card = view.findViewById<CardView>(R.id.carditem)
        val namaSupplier = view.findViewById<TextView>(R.id.tv_namaSupplier)
        val alamatSupplier = view.findViewById<TextView>(R.id.tv_alamatSupplier)
        val telpSupplier = view.findViewById<TextView>(R.id.tv_telpSupplier)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_supplier, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.namaSupplier.text = listSupplier?.get(position)?.namaSupplier
        holder.alamatSupplier.text = listSupplier?.get(position)?.alamat
        holder.telpSupplier.text = listSupplier?.get(position)?.noTelp

        val id = listSupplier?.get(position)?.id
        // card on click intent data to AddBarangActivity
        holder.card.setOnClickListener {
            val intent = Intent(it.context, AddBarangActivity::class.java)
            intent.putExtra("aksi", "create")
            intent.putExtra("idSupplier", id)
            intent.putExtra("namaSupplier", listSupplier?.get(position)?.namaSupplier)
            intent.putExtra("alamatSupplier", listSupplier?.get(position)?.alamat)
            intent.putExtra("telpSupplier", listSupplier?.get(position)?.noTelp)
            it.context.startActivity(intent)
        }
        // card set on click
        // show alert dialog
        holder.card.setOnLongClickListener {
            val alertDialog = AlertDialog.Builder(it.context)
            alertDialog.setTitle("Data Supplier")
            alertDialog.setMessage("Update atau Hapus Data?")
            // choice update button, run update function
            alertDialog.setPositiveButton("Update") { dialogInterface, which ->
                if (id != null) {
                    // kirim data barang ke activity update supplier
                    val intent = Intent(it.context, AddSupplier::class.java)
                    // check update
                    intent.putExtra("aksi", "update")
                    // data supplier
                    intent.putExtra("idSupplier", listSupplier?.get(position)?.id)
                    intent.putExtra("namaSupplier", listSupplier?.get(position)?.namaSupplier)
                    intent.putExtra("alamatSupplier", listSupplier?.get(position)?.alamat)
                    intent.putExtra("telpSupplier", listSupplier?.get(position)?.noTelp)
                    it.context.startActivity(intent)
                }
            }
            // choice delete button, run delete function
            alertDialog.setNegativeButton("Hapus") { dialogInterface, which ->
                if (id != null) {
                    ApiConfigSupplier.getService()
                        .deleteSupplier(id, "Bearer ${SessionManager(it.context).fetchAuthToken()}")
                        .enqueue(object : Callback<RequestSupplier> {
                            override fun onResponse(
                                call: Call<RequestSupplier>,
                                response: Response<RequestSupplier>
                            ) {
                                Toast.makeText(it.context, "Berhasil Dihapus", Toast.LENGTH_SHORT)
                                    .show()
                            }

                            override fun onFailure(call: Call<RequestSupplier>, t: Throwable) {
                                Toast.makeText(it.context, t.localizedMessage, Toast.LENGTH_SHORT)
                                    .show()
                            }

                        })
                }
            }
            alertDialog.setNeutralButton("Cancel") { dialogInterface, which ->
                dialogInterface.dismiss()
            }
            alertDialog.create().show()
            true
        }
    }

    override fun getItemCount(): Int {
        if (listSupplier != null) {
            return listSupplier.size
        }
        return 0
    }
}