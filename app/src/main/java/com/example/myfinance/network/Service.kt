package com.example.myfinance.network

import retrofit2.Call
import com.example.myfinance.data.*
import retrofit2.http.*

interface Service {

    @FormUrlEncoded
    @POST("myfinance/showlogin.php?")
    fun getShowLogin(@Field("username") username : String,
                     @Field("password") password : String): Call<Login>

    @GET("myfinance/showkat.php?")
    fun getDataKategori(): Call<Kategori>

    @GET("myfinance/showkattrans.php?")
    fun getDataKattrans(): Call<Kattrans>

    @FormUrlEncoded
    @POST("myfinance/kategori.php?")
    fun getInsertKat(@Field("action") action : String,
                     @Field("nama") password : String,
                     @Field("kattrans") kattrans : String,
                     @Field("katid") katid : String): Call<CrudKategori>


    @FormUrlEncoded
    @POST("myfinance/kategori.php?")
    fun getEditKat  (@Field("action") action : String,
                     @Field("nama") password : String,
                     @Field("kattrans") kattrans : String,
                     @Field("katid") katid : String): Call<CrudKategori>
    @FormUrlEncoded
    @POST("myfinance/kategori.php?")
    fun getDeleteKat(@Field("action") action : String,
                     @Field("nama") password : String,
                     @Field("kattrans") kattrans : String,
                     @Field("katid") katid : String): Call<CrudKategori>

    @GET("myfinance/showkategori.php?")
    fun getDataKat(): Call<ShowKategori>


}