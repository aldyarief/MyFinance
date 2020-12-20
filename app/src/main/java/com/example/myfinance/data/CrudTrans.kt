package com.example.myfinance.data

import com.google.gson.annotations.SerializedName

data class CrudTrans(

	@field:SerializedName("result")
	val result: Boolean? = null,

	@field:SerializedName("pesan")
	val pesan: String? = null
)
