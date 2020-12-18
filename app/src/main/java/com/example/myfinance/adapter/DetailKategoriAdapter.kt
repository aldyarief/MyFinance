package com.example.myfinance.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myfinance.R
import com.example.myfinance.data.KategorinyaItem
import kotlinx.android.synthetic.main.item_kategori.view.*

class DetailKategoriAdapter (var data: List<KategorinyaItem?>?) : RecyclerView.Adapter<DetailKategoriAdapter.KategoriHolder>() {
    class KategoriHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemKategori = itemView.itemKategori
        val itemKattrans = itemView.itemKattrans
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KategoriHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_kategori, parent,false)
        val holder = KategoriHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: KategoriHolder, position: Int) {
        holder.itemKategori.text = data?.get(position)?.namakat
        holder.itemKattrans.text = data?.get(position)?.namakattrans
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }
}