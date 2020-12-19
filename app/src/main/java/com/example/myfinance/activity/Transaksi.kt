package com.example.myfinance.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.myfinance.R
import java.text.NumberFormat
import java.util.*


class Transaksi : AppCompatActivity() {
    var jmlTrans : EditText? = null
    var nilai : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaksi)
        jmlTrans = findViewById(R.id.jmltrans) as EditText



    }
}