package com.example.myfinance.data

import com.google.gson.annotations.SerializedName

data class ShowTrans(

	@field:SerializedName("transaksinya")
	val transaksinya: List<TransaksinyaItem?>? = null
)

data class TransaksinyaItem(

	@field:SerializedName("jml")
	val jml: String? = null,

	@field:SerializedName("tgl")
	val tgl: String? = null,

	@field:SerializedName("trans_id")
	val transId: String? = null,

	@field:SerializedName("namkat")
	val namkat: String? = null,

	@field:SerializedName("kattrans")
	val kattrans: String? = null
)
