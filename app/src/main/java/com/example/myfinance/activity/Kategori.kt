package com.example.myfinance.activity

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.myfinance.R
import com.example.myfinance.data.HasildataItem
import com.example.myfinance.data.HasilnyaItem
import com.example.myfinance.data.Kattrans
import com.example.myfinance.network.ConfigNetwork
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class Kategori : AppCompatActivity() {
    var server : String? = null
    var Spinner: Spinner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kategori)
        server = "http://aldry.agustianra.my.id/"
        Spinner= findViewById(R.id.SpinKat) as Spinner
        AmbilKat()
    }

    fun AmbilKat() {
        ConfigNetwork.getRetrofit(server!!).getDataKattrans().enqueue(object : Callback<Kattrans> {
            override fun onResponse(call: Call<Kattrans>, response: Response<Kattrans>) {
                if (response.isSuccessful) {

                    val DataKat: List<HasilnyaItem?>? = response.body()?.hasilnya
                    val listSpinner: MutableList<String> = ArrayList()
                    for (i in DataKat!!.indices) {
                        DataKat[i]!!.namakat?.let { listSpinner.add(it) }
                    }

                    val adapter = ArrayAdapter(this@Kategori, android.R.layout.simple_spinner_item, listSpinner)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    Spinner!!.setAdapter(adapter)

                }
            }

            override fun onFailure(call: Call<Kattrans>, t: Throwable) {
                Log.d("response server", t.message!!)
            }


        })
    }
}