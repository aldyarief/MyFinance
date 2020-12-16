package com.example.myfinance.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.myfinance.R
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener

class Dashboard : AppCompatActivity() {
    var images = intArrayOf(
        R.drawable.udacoding1,
        R.drawable.udacoding2,
        R.drawable.udacoding3
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val carouselView = findViewById(R.id.carouselView) as CarouselView;
        carouselView.setPageCount(images.size);
        carouselView.setImageListener(imageListener);

    }

    var imageListener: ImageListener = object : ImageListener {
        override fun setImageForPosition(position: Int, imageView: ImageView) {
            // You can use Glide or Picasso here
            imageView.setImageResource(images[position])
        }
    }

}