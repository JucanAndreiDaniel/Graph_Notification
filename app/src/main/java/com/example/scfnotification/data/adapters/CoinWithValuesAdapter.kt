package com.example.scfnotification.data.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.scfnotification.R
import com.example.scfnotification.data.entities.CoinWithValues
import com.example.scfnotification.ui.home.HomeFragmentDirections

class CoinWithValuesAdapter(ct: Context) :
    RecyclerView.Adapter<CoinWithValuesAdapter.CoinsViewHolder>() {

    private var context: Context = ct
    private val layoutInflater: LayoutInflater = LayoutInflater.from(ct)
    private var coinWithValuesList: List<CoinWithValues> = listOf()

    fun setCoinWithValuesList(coinWithValuesList: List<CoinWithValues>) {
        this.coinWithValuesList = coinWithValuesList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinsViewHolder {
        val itemView = layoutInflater.inflate(R.layout.row_layout, parent, false)
        return CoinsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CoinsViewHolder, position: Int) {

        val coinWithValues = coinWithValuesList[position]
        val coinValue = coinWithValues.values
        if (coinValue.isNotEmpty()) {
            holder.currencyName.text = coinWithValues.coin.name
            holder.current.text = coinValue[0].current.toString()
            holder.high.text = coinValue[0].high_1d.toString()
            holder.low.text = coinValue[0].low_1d.toString()
            val direction =
                HomeFragmentDirections.actionNavigationHomeToNavigationDetail(coinWithValues.coin.id)
            holder.itemView.setOnClickListener { view ->
                view.findNavController().navigate(direction)
            }
        }
    }

    override fun getItemCount(): Int {
        return coinWithValuesList.size
    }

    class CoinsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var currencyName: TextView = itemView.findViewById(R.id.currencyName)
        var current: TextView = itemView.findViewById(R.id.current)
        var high: TextView = itemView.findViewById(R.id.high_1d)
        var low: TextView = itemView.findViewById(R.id.low_1d)
    }
}
