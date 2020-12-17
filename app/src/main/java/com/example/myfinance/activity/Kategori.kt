package com.example.myfinance.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.myfinance.R
import com.example.myfinance.data.Kattrans
import com.example.myfinance.data.Login
import com.example.myfinance.network.ConfigNetwork
import org.json.JSONException
import org.json.JSONObject
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
        ConfigNetwork.getRetrofit(server!!).getDataKattrans().enqueue(object :
            Callback<Kattrans> {
            override fun onResponse(call: Call<Kattrans>, response: Response<Kattrans>) {
                if (response.isSuccessful){

                    val hasil = response.body()?.hasilnya
                }
            }

            override fun onFailure(call: Call<Kattrans>, t: Throwable) {
                Log.d("response server", t.message!!)
            }


        })
    }
}