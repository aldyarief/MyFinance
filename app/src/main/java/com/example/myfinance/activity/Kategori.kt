package com.example.myfinance.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import com.example.myfinance.R
import com.example.myfinance.data.CrudKategori
import com.example.myfinance.data.HasilnyaItem
import com.example.myfinance.data.Kattrans
import com.example.myfinance.detailactivity.KategoriDetail
import com.example.myfinance.network.ConfigNetwork
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class Kategori : AppCompatActivity() {
    var server : String? = null
    var Spinner: Spinner? = null
    var IDKAT: TextView? = null
    var edkat: EditText? = null
    var btnSimpan : Button? = null
    var btnClose : Button? = null
    var btnShow : Button? = null
    var action : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kategori)
        server = "http://aldry.agustianra.my.id/"
        Spinner= findViewById(R.id.SpinKat) as Spinner
        IDKAT = findViewById(R.id.idkat) as TextView
        edkat = findViewById(R.id.namkat) as EditText
        btnSimpan = findViewById(R.id.SimpanBtn) as Button
        btnClose = findViewById(R.id.CloseBtn) as Button
        btnShow = findViewById(R.id.Showbtn) as Button
        AmbilKat()

        btnSimpan!!.setOnClickListener {
            val namkat = edkat!!.text.toString().trim { it <= ' ' }
            val kattrans = IDKAT!!.text.toString().trim { it <= ' ' }
            action = "insertdata"

            ConfigNetwork.getRetrofit(server!!).getInsertKat(action!!,namkat!!,kattrans!!).enqueue(object : Callback<com.example.myfinance.data.CrudKategori> {
                override fun onResponse(call: Call<CrudKategori>, response: Response<CrudKategori>) {
                    Log.d("response server", response.message())

                    if (response.isSuccessful) {
                        val hasilnya = response.body()?.pesan
                        Toast.makeText(this@Kategori, hasilnya, Toast.LENGTH_SHORT).show()
                        edkat!!.getText().clear()
                        action=""

                    }
                }

                override fun onFailure(call: Call<CrudKategori>, t: Throwable) {
                    Log.d("response server", t.message!!)
                }

            })
        }

        btnClose!!.setOnClickListener {
            val intent = Intent(this@Kategori,Dashboard ::class.java)
            startActivity(intent)
            finish()
        }

        btnShow!!.setOnClickListener {
            val intent = Intent(this@Kategori,KategoriDetail ::class.java)
            startActivity(intent)
            finish()
        }

        Spinner!!.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedName = parent.getItemAtPosition(position).toString()
                IDKAT!!.setText(selectedName)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
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

                    val adapter = ArrayAdapter(this@Kategori,android.R.layout.simple_spinner_item, listSpinner)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    Spinner!!.setAdapter(adapter)

                }
            }

            override fun onFailure(call: Call<Kattrans>, t: Throwable) {
                Log.d("response server", t.message!!)
            }


        })
    }

    override fun onBackPressed() {
        val intent = Intent(this@Kategori,Dashboard ::class.java)
        startActivity(intent)
        finish()
    }
}