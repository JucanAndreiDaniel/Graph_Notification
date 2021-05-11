package com.example.scfnotification.data.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.scfnotification.R
import com.example.scfnotification.data.retrofit.Coin

class BaseRecyclerAdapter(ct: Context) :
        RecyclerView.Adapter<BaseRecyclerAdapter.BaseViewHolder>() {

    private var coinList: List<Coin> = listOf()
    private var context: Context = ct

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(context)

        return BaseViewHolder(inflater.inflate(R.layout.row_layout, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.currencyName.text = coinList[position].name
        holder.current.text = coinList[position].current
        holder.high_1d.text = coinList[position].high_1d
        holder.low_1d.text = coinList[position].low_1d
    }

    override fun getItemCount(): Int {
        return coinList.size
    }
    fun setCoinListItems(coinList: List<Coin>){
        this.coinList = coinList
        notifyDataSetChanged()
    }
    class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var currencyName: TextView = itemView.findViewById(R.id.currencyName)
        var current: TextView = itemView.findViewById(R.id.current)
        var high_1d: TextView = itemView.findViewById(R.id.high_1d)
        var low_1d: TextView = itemView.findViewById(R.id.low_1d)
    }
}