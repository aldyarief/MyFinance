package com.example.myfinance.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
    var edkat: EditText? = null
    var btnSimpan : Button? = null
    var btnClose : Button? = null
    var btnShow : Button? = null
    var action : String? = null
    var income : RadioButton? = null
    var expenses : RadioButton? = null
    var kattrans : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kategori)
        server = "http://aldry.agustianra.my.id/"
        edkat = findViewById(R.id.namkat) as EditText
        btnSimpan = findViewById(R.id.SimpanBtn) as Button
        btnClose = findViewById(R.id.CloseBtn) as Button
        btnShow = findViewById(R.id.Showbtn) as Button
        income = findViewById(R.id.income) as RadioButton
        expenses = findViewById(R.id.expenses) as RadioButton
        val namakat: String? = intent.getStringExtra("namakat")
        val idkat: String? = intent.getStringExtra("idkat")
        action = intent.getStringExtra("action")

        if (action.equals("editdata")) {
            action="editdata"

            if (idkat.equals("1") ){
                income!!.isChecked = true
                expenses!!.isChecked = false
            } else {
                expenses!!.isChecked = true
                income!!.isChecked = false
            }
            edkat!!.setText(namakat)

        } else {
            action=""
        }


        btnSimpan!!.setOnClickListener {
            val namkat = edkat!!.text.toString().trim { it <= ' ' }

            if (income!!.isChecked) {
                kattrans = 1.toString()
            } else if (expenses!!.isChecked) {
                kattrans = 2.toString()
            } else {
                Toast.makeText(this@Kategori, "Akun tidak dipilih", Toast.LENGTH_SHORT).show()
                income!!.requestFocus()
            }

            val katid="-"
            if (action.equals("")) {
                action = "insertdata"
            }

            if (action.equals("insertdata")) {

                ConfigNetwork.getRetrofit(server!!).getInsertKat(action!!, namkat!!, kattrans!!, katid!!).enqueue(object : Callback<com.example.myfinance.data.CrudKategori> {
                    override fun onResponse(call: Call<CrudKategori>, response: Response<CrudKategori>) {
                        Log.d("response server", response.message())

                        if (response.isSuccessful) {
                            val hasilnya = response.body()?.pesan
                            Toast.makeText(this@Kategori, hasilnya, Toast.LENGTH_SHORT).show()
                            edkat!!.getText().clear()
                            action = ""

                        }
                    }

                    override fun onFailure(call: Call<CrudKategori>, t: Throwable) {
                        Log.d("response server", t.message!!)
                    }

                })
            } else if (action.equals("editdata")) {
                val namkat = edkat!!.text.toString().trim { it <= ' ' }
                if (income!!.isChecked) {
                    kattrans = 1.toString()
                } else if (expenses!!.isChecked) {
                    kattrans = 2.toString()
                } else {
                    Toast.makeText(this@Kategori, "Akun tidak dipilih atau dipilih lebih dari satu!", Toast.LENGTH_SHORT).show()
                    income!!.requestFocus()
                }
                val katid=idkat
                ConfigNetwork.getRetrofit(server!!).getEditKat(action!!, namkat!!, kattrans!!, katid!!).enqueue(object : Callback<com.example.myfinance.data.CrudKategori> {
                    override fun onResponse(call: Call<CrudKategori>, response: Response<CrudKategori>) {
                        Log.d("response server", response.message())

                        if (response.isSuccessful) {
                            val hasilnya = response.body()?.pesan
                            Toast.makeText(this@Kategori, hasilnya, Toast.LENGTH_SHORT).show()
                            edkat!!.getText().clear()
                            action = ""

                        }
                    }

                    override fun onFailure(call: Call<CrudKategori>, t: Throwable) {
                        Log.d("response server", t.message!!)
                    }

                })
            }
        }

        btnClose!!.setOnClickListener {
            val intent = Intent(this@Kategori, Dashboard::class.java)
            startActivity(intent)
            finish()
        }

        btnShow!!.setOnClickListener {
            val intent = Intent(this@Kategori, KategoriDetail::class.java)
            startActivity(intent)
            finish()
        }

    }

    override fun onBackPressed() {
        val intent = Intent(this@Kategori, Dashboard::class.java)
        startActivity(intent)
        finish()
    }
}