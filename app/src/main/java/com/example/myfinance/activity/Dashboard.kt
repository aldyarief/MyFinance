package com.example.myfinance.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.myfinance.R
import com.example.myfinance.data.ShowEarnings
import com.example.myfinance.data.ShowKategori
import com.example.myfinance.network.ConfigNetwork
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*

class Dashboard : AppCompatActivity() {
    var images = intArrayOf(
        R.drawable.udacoding1,
        R.drawable.udacoding2,
        R.drawable.udacoding3
    )
    var kategori: LinearLayout?= null
    var trans: LinearLayout?= null
    var laporan: LinearLayout?= null
    var server : String? = null
    var total : TextView? = null
    var hasil : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        kategori = findViewById<View>(R.id.kat) as LinearLayout
        trans = findViewById<View>(R.id.bank) as LinearLayout
        laporan = findViewById<View>(R.id.laporan) as LinearLayout
        total = findViewById(R.id.total) as TextView
        server = "http://aldry.agustianra.my.id/"

        AmbilData()
        val carouselView = findViewById(R.id.carouselView) as CarouselView;
        carouselView.setPageCount(images.size);
        carouselView.setImageListener(imageListener);

        kategori!!.setOnClickListener {
            val intent = Intent(this@Dashboard, Kategori::class.java)
            startActivity(intent)
            finish()
        }

        trans!!.setOnClickListener {
            val intent = Intent(this@Dashboard, Transaksi::class.java)
            startActivity(intent)
            finish()
        }


    }

    var imageListener: ImageListener = object : ImageListener {
        override fun setImageForPosition(position: Int, imageView: ImageView) {
            imageView.setImageResource(images[position])
        }
    }

    override fun onBackPressed() {
        finish()
    }

    fun AmbilData() {
        val localeID = Locale("in", "ID")
        val formatRupiah: NumberFormat = NumberFormat.getCurrencyInstance(localeID)
        ConfigNetwork.getRetrofit(server!!).getTotalEarnings().enqueue(object : Callback<ShowEarnings> {

            override fun onResponse(call: Call<ShowEarnings>, response: Response<ShowEarnings>) {
                Log.d("response server", response.message())

                if (response.isSuccessful){
                    val datanya = response.body()?.total
                    hasil = datanya!!.toInt()
                    total!!.setText(formatRupiah.format(hasil))
                }
            }

            override fun onFailure(call: Call<ShowEarnings>, t: Throwable) {
                Log.d("response server", t.message!!)
            }
        })

    }

}