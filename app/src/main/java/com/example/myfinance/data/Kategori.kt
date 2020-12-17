package com.example.myfinance.data

import com.google.gson.annotations.SerializedName

data class Kategori(

	@field:SerializedName("hasildata")
	val hasildata: List<HasildataItem?>? = null
)

data class HasildataItem(

	@field:SerializedName("kategoriid")
	val kategoriid: String? = null,

	@field:SerializedName("kattrans_id")
	val kattransId: String? = null,

	@field:SerializedName("namakat")
	val namakat: String? = null
)
