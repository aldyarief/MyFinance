package com.example.myfinance.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.myfinance.R
import com.example.myfinance.data.Login
import com.example.myfinance.network.ConfigNetwork
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity() {
    var server : String? = null
    var nama: EditText? = null
    var pass: EditText? = null
    var login: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        server = "http://aldry.agustianra.my.id/"
        nama = findViewById(R.id.username) as EditText
        pass = findViewById(R.id.password) as EditText
        login = findViewById<View>(R.id.loginBtn) as Button

        login!!.setOnClickListener {
            val username = nama!!.text.toString().trim { it <= ' ' }
            val password = pass!!.text.toString().trim { it <= ' ' }

            ConfigNetwork.getRetrofit(server!!).getShowLogin(username!!,password!!).enqueue(object : Callback<com.example.myfinance.data.Login> {
                override fun onResponse(call: Call<Login>, response: Response<Login>) {
                    Log.d("response server", response.message())

                    if (response.isSuccessful){

                        val hasil = response.body()?.pesan

                        if (hasil.equals("OK")) {
                            val intent = Intent(this@Login,Dashboard ::class.java)
                            startActivity(intent)
                            finish()
                        }

                    }
                }

                override fun onFailure(call: Call<Login>, t: Throwable) {
                    Log.d("response server", t.message!!)
                }


            })


        }
    }

}