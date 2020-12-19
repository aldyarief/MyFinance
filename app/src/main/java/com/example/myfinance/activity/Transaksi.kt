package com.example.myfinance.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.myfinance.R
import com.example.myfinance.data.HasilnyaItem
import com.example.myfinance.data.Kattrans
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaksi)
        server = "http://aldry.agustianra.my.id/"
        Spinner= findViewById(R.id.SpinKat) as Spinner
        jmlTrans = findViewById(R.id.jmltrans) as EditText

        jmlTrans!!.addTextChangedListener(object : TextWatcher {
            private var current = ""
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (!s.toString().equals(current)) {
                    jmlTrans!!.removeTextChangedListener(this)
                    val local = Locale("id", "id")
                    val replaceable = String.format("[Rp,.\\s]", NumberFormat.getCurrencyInstance().currency.getSymbol(local))
                    val cleanString: String = s.toString().replace(replaceable.toRegex(), "")
                    val parsed: Double
                    parsed = try {
                        cleanString.toDouble()
                    } catch (e: NumberFormatException) {
                        0.00
                    }
                    val formatter = NumberFormat.getCurrencyInstance(local)
                    formatter.maximumFractionDigits = 0
                    formatter.isParseIntegerOnly = true
                    val formatted = formatter.format(parsed)
                    val replace = String.format("[Rp\\s]", NumberFormat.getCurrencyInstance().currency.getSymbol(local))
                    val clean = formatted.replace(replace.toRegex(), "")
                    current = formatted
                    jmlTrans!!.setText(clean)
                    jmlTrans!!.setSelection(clean.length)
                    jmlTrans!!.addTextChangedListener(this)
                }
            }

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

                    val adapter = ArrayAdapter(this@Transaksi, android.R.layout.simple_spinner_item, listSpinner)
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