package com.example.myfinance.detailactivity

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.myfinance.R
import com.example.myfinance.activity.Kategori
import com.example.myfinance.adapter.OnDeleteTransClickListener
import com.example.myfinance.adapter.ShowTransAdapter
import com.example.myfinance.data.CrudKategori
import com.example.myfinance.data.CrudTrans
import com.example.myfinance.data.ShowTrans
import com.example.myfinance.data.TransaksinyaItem
import com.example.myfinance.network.ConfigNetwork
import kotlinx.android.synthetic.main.activity_trans_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransDetail : AppCompatActivity(), OnDeleteTransClickListener {
    var server : String? = null
    var action : String? = null
    var namkat : String? = null
    var kattrans : String? = null
    var tgl : String? = null
    var jml : String? = null
    var katid : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trans_detail)
        server = "http://aldry.agustianra.my.id/"
        AmbilData()
    }
    private fun showData(datatrans: List<TransaksinyaItem?>?) {
        listtrans.adapter = ShowTransAdapter(datatrans,this)
    }

    fun AmbilData() {
        ConfigNetwork.getRetrofit(server!!).getDatatrans().enqueue(object : Callback<com.example.myfinance.data.ShowTrans> {

            override fun onResponse(call: Call<ShowTrans>, response: Response<ShowTrans>) {
                Log.d("response server", response.message())

                if (response.isSuccessful){

                    val datakategori = response.body()?.transaksinya

                    showData(datakategori)
                }
            }

            override fun onFailure(call: Call<ShowTrans>, t: Throwable) {
                Log.d("response server", t.message!!)
            }
        })

    }

    override fun onBackPressed() {
        val intent = Intent(this@TransDetail, Kategori ::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDelete(item: TransaksinyaItem?, position: Int) {
        lateinit var dialog: AlertDialog
        action = "deletedata"
        namkat = item!!.namkat
        jml= item!!.jml
        tgl = item!!.tgl
        val idtrans = item.transId


        val builder = AlertDialog.Builder(this)
        builder.setTitle("My Finance")
        builder.setMessage("Yakin akan menghapus data transaksi?")

        val dialogClickListener = DialogInterface.OnClickListener{ _, which ->
            when(which){

                DialogInterface.BUTTON_POSITIVE ->
                    ConfigNetwork.getRetrofit(server!!).getDeleteTrans(action!!, namkat!!, jml!!,tgl!!,idtrans!!).enqueue(object : Callback<com.example.myfinance.data.CrudTrans> {
                        override fun onResponse(call: Call<CrudTrans>, response: Response<CrudTrans>) {
                            Log.d("response server", response.message())

                            if (response.isSuccessful) {
                                val hasilnya = response.body()?.pesan
                                Toast.makeText(this@TransDetail, hasilnya, Toast.LENGTH_SHORT).show()
                                action = ""
                                AmbilData()

                            }
                        }

                        override fun onFailure(call: Call<CrudTrans>, t: Throwable) {
                            Log.d("response server", t.message!!)
                        }

                    })
                DialogInterface.BUTTON_NEGATIVE -> dialog.dismiss();
            }
        }


        builder.setPositiveButton("YES",dialogClickListener)
        builder.setNegativeButton("NO",dialogClickListener)
        dialog = builder.create()
        dialog.show()
    }
}