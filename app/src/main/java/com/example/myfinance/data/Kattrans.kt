package com.example.myfinance.data

import com.google.gson.annotations.SerializedName

data class Kattrans(

	@field:SerializedName("hasilnya")
	val hasilnya: List<HasilnyaItem?>? = null
)

data class HasilnyaItem(

	@field:SerializedName("kattrans_id")
	val kattransId: String? = null,

	@field:SerializedName("namakat")
	val namakat: String? = null
)
