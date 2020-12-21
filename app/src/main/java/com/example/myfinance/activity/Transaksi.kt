package com.example.myfinance.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.myfinance.R
import com.example.myfinance.data.*
import com.example.myfinance.detailactivity.KategoriDetail
import com.example.myfinance.detailactivity.TransDetail
import com.example.myfinance.network.ConfigNetwork
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class Transaksi : AppCompatActivity() {
    var jmlTrans : EditText? = null
    var server : String? = null
    var Spinner: Spinner? = null
    var btnSimpan : Button? = null
    var btnTutup : Button? = null
    var btnShow : Button? = null
    var action : String? = null
    var katid : TextView? = null
    var datePickerDialog: DatePickerDialog? = null
    var dateFormatter: SimpleDateFormat? = null
    var txDate: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaksi)
        server = "http://aldry.agustianra.my.id/"
        Spinner= findViewById(R.id.SpinKat) as Spinner
        jmlTrans = findViewById(R.id.jmltrans) as EditText
        btnSimpan = findViewById(R.id.SimpanBtn) as Button
        btnTutup = findViewById(R.id.CloseBtn) as Button
        btnShow = findViewById(R.id.Showbtn) as Button
        katid = findViewById(R.id.idkat) as TextView
        action=""
        dateFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.US)
        txDate = findViewById<View>(R.id.tv_dateresult) as TextView
        AmbilKat()

        txDate!!.setOnClickListener {
            showDateDialog()
        }

        btnShow!!.setOnClickListener {
            val intent = Intent(this@Transaksi, TransDetail::class.java)
            startActivity(intent)
            finish()
        }
        btnSimpan!!.setOnClickListener {
            action="insertdata"
            val jml = jmlTrans!!.text.toString().trim { it <= ' ' }
            val namkat = katid!!.text.toString().trim { it <= ' ' }
            val tgl = txDate!!.text.toString().trim { it <= ' ' }
            ConfigNetwork.getRetrofit(server!!).getInsertTrans(action!!, namkat!!, jml!!,tgl!!).enqueue(object : Callback<com.example.myfinance.data.CrudTrans> {
                override fun onResponse(call: Call<CrudTrans>, response: Response<CrudTrans>) {
                    Log.d("response server", response.message())

                    if (response.isSuccessful) {
                        val hasilnya = response.body()?.pesan
                        Toast.makeText(this@Transaksi, hasilnya, Toast.LENGTH_SHORT).show()
                        jmlTrans!!.getText().clear()
                        txDate!!.text=""
                        action = ""

                    }
                }

                override fun onFailure(call: Call<CrudTrans>, t: Throwable) {
                    Log.d("response server", t.message!!)
                }

            })
        }

        btnTutup!!.setOnClickListener {
            val intent = Intent(this@Transaksi, Dashboard::class.java)
            startActivity(intent)
            finish()
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
        ConfigNetwork.getRetrofit(server!!).getDataKategori().enqueue(object : Callback<com.example.myfinance.data.Kategori> {


            override fun onResponse(call: Call<com.example.myfinance.data.Kategori>, response: Response<com.example.myfinance.data.Kategori>) {
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

            override fun onFailure(call: Call<com.example.myfinance.data.Kategori>, t: Throwable) {
                Log.d("response server", t.message!!)
            }
        })
    }

    override fun onBackPressed() {
        val intent = Intent(this@Transaksi, Dashboard::class.java)
        startActivity(intent)
        finish()
    }

    private fun showDateDialog() {

        val newCalendar = Calendar.getInstance()
        datePickerDialog = DatePickerDialog(this, { view, year, monthOfYear, dayOfMonth ->
            val newDate = Calendar.getInstance()
            newDate[year, monthOfYear] = dayOfMonth
            txDate!!.text = dateFormatter!!.format(newDate.time)
        }, newCalendar[Calendar.YEAR], newCalendar[Calendar.MONTH], newCalendar[Calendar.DAY_OF_MONTH])
        datePickerDialog!!.show()
    }
}