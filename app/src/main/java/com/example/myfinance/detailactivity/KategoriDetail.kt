package com.example.myfinance.detailactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myfinance.R
import com.example.myfinance.activity.Dashboard
import com.example.myfinance.activity.Kategori
import com.example.myfinance.adapter.DetailKategoriAdapter
import com.example.myfinance.data.KategorinyaItem
import com.example.myfinance.data.ShowKategori
import com.example.myfinance.network.ConfigNetwork
import kotlinx.android.synthetic.main.activity_kategori_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KategoriDetail : AppCompatActivity() {
    var server : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kategori_detail)
        server = "http://aldry.agustianra.my.id/"
        AmbilData()
    }

    private fun showData(datakategori: List<KategorinyaItem?>?) {
        listkategori.adapter = DetailKategoriAdapter(datakategori)
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
}