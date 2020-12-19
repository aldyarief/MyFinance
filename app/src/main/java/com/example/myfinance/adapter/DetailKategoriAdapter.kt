package com.example.myfinance.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myfinance.R
import com.example.myfinance.data.KategorinyaItem
import kotlinx.android.synthetic.main.item_kategori.view.*

class DetailKategoriAdapter (var data: List<KategorinyaItem?>?,
                             private val itemClick : OnEditItemClikListener,
                             private val deleteClick : OnDeleteItemClickListener) : RecyclerView.Adapter<DetailKategoriAdapter.KategoriHolder>() {
    class KategoriHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemKategori = itemView.itemKategori
        val itemKattrans = itemView.itemKattrans
        val itemKatid = itemView.itemKatid
        val menuedit = itemView.menuedit
        val menudelete = itemView.menudelete

        fun initialize(item : KategorinyaItem?, action: OnEditItemClikListener) {

            menuedit.setOnClickListener{
                action.onItemClick(item,adapterPosition)
            }
        }

        fun initialize(item: KategorinyaItem?, action: OnDeleteItemClickListener) {
            menudelete.setOnClickListener{
                action.onDelete(item,adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KategoriHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_kategori, parent,false)
        val holder = KategoriHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: KategoriHolder, position: Int) {
        holder.itemKategori.text = data?.get(position)?.namakat
        holder.itemKattrans.text = data?.get(position)?.namakattrans
        holder.itemKatid.text = data?.get(position)?.kategoriId
        holder.initialize(data?.get(position),itemClick)
        holder.initialize(data?.get(position),deleteClick)
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }
}


interface OnDeleteItemClickListener {
    fun onDelete(item: KategorinyaItem?, position: Int)
}

interface OnEditItemClikListener {
    fun onItemClick(item: KategorinyaItem?, position: Int)
}