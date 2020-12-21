package com.example.myfinance.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myfinance.R
import com.example.myfinance.data.KategorinyaItem
import com.example.myfinance.data.TransaksinyaItem
import kotlinx.android.synthetic.main.item_trans.view.*
import java.text.NumberFormat
import java.util.*

class ShowTransAdapter(var data: List<TransaksinyaItem?>?,
                       private val deleteClick : OnDeleteTransClickListener) : RecyclerView.Adapter<ShowTransAdapter.TransHolder>()  {
    class TransHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val itemJumlah = itemView.itemJml
    val itemKategori = itemView.itemKategori
    val itemKattrans = itemView.itemKattrans
    val itemTransid = itemView.itemTransid
    val itemDate = itemView.itemDate
    val menudelete = itemView.menudelete


    fun initialize(item: TransaksinyaItem?, action: OnDeleteTransClickListener) {
        menudelete.setOnClickListener{
            action.onDelete(item,adapterPosition)
        }
    }

}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_trans, parent,false)
        val holder = TransHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: TransHolder, position: Int) {
        val localeID = Locale("in", "ID")
        val formatRupiah: NumberFormat = NumberFormat.getCurrencyInstance(localeID )
        val jumlahnya = data?.get(position)?.jml!!.toInt()
        holder.itemJumlah.text = "Total Transaksi : ${formatRupiah.format(jumlahnya)}"
        holder.itemDate.text = data?.get(position)?.tgl
        holder.itemKategori.text = data?.get(position)?.namkat
        holder.itemKattrans.text = data?.get(position)?.kattrans
        holder.itemTransid.text = data?.get(position)?.transId
        holder.initialize(data?.get(position),deleteClick)
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }
}

interface OnDeleteTransClickListener {
    fun onDelete(item: TransaksinyaItem?, position: Int)
}