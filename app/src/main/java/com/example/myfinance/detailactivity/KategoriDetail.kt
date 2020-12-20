package com.example.myfinance.detailactivity

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.myfinance.R
import com.example.myfinance.activity.Dashboard
import com.example.myfinance.activity.Kategori
import com.example.myfinance.adapter.DetailKategoriAdapter
import com.example.myfinance.adapter.OnDeleteItemClickListener
import com.example.myfinance.adapter.OnEditItemClikListener
import com.example.myfinance.data.CrudKategori
import com.example.myfinance.data.HasilnyaItem
import com.example.myfinance.data.KategorinyaItem
import com.example.myfinance.data.ShowKategori
import com.example.myfinance.network.ConfigNetwork
import kotlinx.android.synthetic.main.activity_kategori_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class KategoriDetail : AppCompatActivity(), OnEditItemClikListener, OnDeleteItemClickListener {
    var server : String? = null
    var action : String? = null
    var nama : String? = null
    var kattrans : String? = null
    var katid : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kategori_detail)
        server = "http://aldry.agustianra.my.id/"
        AmbilData()
    }

    private fun showData(datakategori: List<KategorinyaItem?>?) {
        listkategori.adapter = DetailKategoriAdapter(datakategori,this,this)
    }

    fun AmbilData() {
        ConfigNetwork.getRetrofit(server!!).getDataKat().enqueue(object : Callback<com.example.myfinance.data.ShowKategori> {

            override fun onResponse(call: Call<ShowKategori>, response: Response<ShowKategori>) {
                Log.d("response server", response.message())

                if (response.isSuccessful){

                    val datakategori = response.body()?.kategorinya

                    showData(datakategori)
                }
            }
            override fun onFailure(call: Call<ShowKategori>, t: Throwable) {
                Log.d("response server", t.message!!)
            }
        })

    }

    override fun onBackPressed() {
        val intent = Intent(this@KategoriDetail, Kategori ::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDelete(item: KategorinyaItem?, position: Int) {
        lateinit var dialog: AlertDialog
        action="deletedata"
        nama = "-"
        kattrans="-"
        katid = item!!.kategoriId


        val builder = AlertDialog.Builder(this)
        builder.setTitle("My Finance")
        builder.setMessage("Yakin akan menghapus data kategori?")

        val dialogClickListener = DialogInterface.OnClickListener{ _, which ->
            when(which){

                DialogInterface.BUTTON_POSITIVE ->
                    ConfigNetwork.getRetrofit(server!!).getDeleteKat(action!!,nama!!,kattrans!!,katid!!).enqueue(object : Callback<com.example.myfinance.data.CrudKategori> {
                        override fun onResponse(call: Call<CrudKategori>, response: Response<CrudKategori>) {
                            Log.d("response server", response.message())

                            if (response.isSuccessful) {
                                val hasilnya = response.body()?.pesan
                                Toast.makeText(this@KategoriDetail, hasilnya, Toast.LENGTH_SHORT).show()
                                AmbilData()
                                action=""

                            }
                        }

                        override fun onFailure(call: Call<CrudKategori>, t: Throwable) {
                            Log.d("response server", t.message!!)
                        }

                    })

                DialogInterface.BUTTON_NEGATIVE -> dialog.dismiss();
            }
        }


        builder.setPositiveButton("YES",dialogClickListener)
        builder.setNegativeButton("NO",dialogClickListener)
        dialog = builder.create()
        dialog.show()
    }

    override fun onItemClick(item: KategorinyaItem?, position: Int) {
        val intent = Intent(this@KategoriDetail, Kategori::class.java)
        intent.putExtra("namakat", item?.namakat)
        intent.putExtra("idkat", item?.kategoriId)
        intent.putExtra("action", "editdata")
        startActivity(intent)
        finish()
    }
}