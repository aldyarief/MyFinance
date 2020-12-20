package com.example.myfinance.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.myfinance.R
import com.example.myfinance.data.*
import com.example.myfinance.data.Kategori
import com.example.myfinance.network.ConfigNetwork
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*
import java.util.Collections.replaceAll


class Transaksi : AppCompatActivity() {
    var jmlTrans : EditText? = null
    var server : String? = null
    var Spinner: Spinner? = null
    var btnSimpan : Button? = null
    var action : String? = null
    var katid : TextView? = null
    var uangnya : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaksi)
        server = "http://aldry.agustianra.my.id/"
        Spinner= findViewById(R.id.SpinKat) as Spinner
        jmlTrans = findViewById(R.id.jmltrans) as EditText
        btnSimpan = findViewById(R.id.SimpanBtn) as Button
        katid = findViewById(R.id.idkat) as TextView
        action=""
        AmbilKat()

        btnSimpan!!.setOnClickListener {
            action="insertdata"
            val jml = jmlTrans!!.text.toString().trim { it <= ' ' }
            val namkat = katid!!.text.toString().trim { it <= ' ' }
            ConfigNetwork.getRetrofit(server!!).getInsertTrans(action!!, namkat!!, jml!!).enqueue(object : Callback<com.example.myfinance.data.CrudTrans> {
                override fun onResponse(call: Call<CrudTrans>, response: Response<CrudTrans>) {
                    Log.d("response server", response.message())

                    if (response.isSuccessful) {
                        val hasilnya = response.body()?.pesan
                        Toast.makeText(this@Transaksi, hasilnya, Toast.LENGTH_SHORT).show()
                        jmlTrans!!.getText().clear()
                        action = ""

                    }
                }

                override fun onFailure(call: Call<CrudTrans>, t: Throwable) {
                    Log.d("response server", t.message!!)
                }

            })
        }

        Spinner!!.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedName = parent.getItemAtPosition(position).toString()
                katid!!.setText(selectedName)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

    }

    fun AmbilKat() {
        ConfigNetwork.getRetrofit(server!!).getDataKategori().enqueue(object : Callback<Kategori> {

            override fun onResponse(call: Call<Kategori>, response: Response<Kategori>) {
                if (response.isSuccessful) {

                    val DataKat: List<HasildataItem?>? = response.body()?.hasildata
                    val listSpinner: MutableList<String> = ArrayList()
                    for (i in DataKat!!.indices) {
                        DataKat[i]!!.namakat?.let { listSpinner.add(it) }
                    }

                    val adapter = ArrayAdapter(this@Transaksi, android.R.layout.simple_spinner_item, listSpinner)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    Spinner!!.setAdapter(adapter)

                }
            }

            override fun onFailure(call: Call<Kategori>, t: Throwable) {
                Log.d("response server", t.message!!)
            }


        })
    }

    override fun onBackPressed() {
        val intent = Intent(this@Transaksi, Dashboard::class.java)
        startActivity(intent)
        finish()
    }
}