package com.example.myfinance.network

import retrofit2.Call
import com.example.myfinance.data.*
import retrofit2.http.*

interface Service {

    @FormUrlEncoded
    @POST("mykasirku/showlogin.php?")
    fun getShowLogin(@Field("username") username : String,
                     @Field("password") password : String): Call<Login>
}