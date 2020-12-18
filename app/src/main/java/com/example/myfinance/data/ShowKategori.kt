package com.example.myfinance.data

import com.google.gson.annotations.SerializedName

data class ShowKategori(

	@field:SerializedName("kategorinya")
	val kategorinya: List<KategorinyaItem?>? = null
)

data class KategorinyaItem(

	@field:SerializedName("kategori_id")
	val kategoriId: String? = null,

	@field:SerializedName("namakattrans")
	val namakattrans: String? = null,

	@field:SerializedName("namakat")
	val namakat: String? = null
)
