package com.example.myfinance.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.myfinance.R
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener

class Dashboard : AppCompatActivity() {
    var images = intArrayOf(
        R.drawable.udacoding1,
        R.drawable.udacoding2,
        R.drawable.udacoding3
    )
    var kategori: LinearLayout?= null
    var trans: LinearLayout?= null
    var laporan: LinearLayout?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        kategori = findViewById<View>(R.id.kat) as LinearLayout
        trans = findViewById<View>(R.id.bank) as LinearLayout
        laporan = findViewById<View>(R.id.laporan) as LinearLayout

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

}