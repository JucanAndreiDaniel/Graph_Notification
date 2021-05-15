package com.example.scfnotification.data.adapters

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.scfnotification.R
import com.example.scfnotification.data.entities.CoinWithValues

class CoinWithValuesAdapter(ct: Context) :
    RecyclerView.Adapter<CoinWithValuesAdapter.CoinsViewHolder>() {

    private var context: Context = ct
    private val layoutInflater: LayoutInflater = LayoutInflater.from(ct)
    private var coinWithValuesList: List<CoinWithValues>? = null

    fun setCoinWithValuesList(coinWithValuesList: List<CoinWithValues>) {
        this.coinWithValuesList = coinWithValuesList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinsViewHolder {
        val itemView = layoutInflater.inflate(R.layout.row_layout, parent, false)
        return CoinsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CoinsViewHolder, position: Int) {

        val coinWithValues = coinWithValuesList!![position]
        val coinValue = coinWithValues.values
        if (coinValue.isNotEmpty()) {
            holder.currencyName.text = coinWithValues.coin.name
            holder.current.text = coinValue[0].current.toString()
            holder.high.text = coinValue[0].high_1d.toString()
            holder.low.text = coinValue[0].low_1d.toString()
            holder.itemView.setOnClickListener {
                val dialog = Dialog(context)
                dialog.setContentView(R.layout.details_fragment)
                val coin_name: TextView = dialog.findViewById(R.id.coin_name)
                val current: TextView = dialog.findViewById(R.id.current_price)
                val high: TextView = dialog.findViewById(R.id.high)
                val low: TextView = dialog.findViewById(R.id.low)
                coin_name.text = coinWithValues.coin.name
                current.text = coinWithValues.values[0].current.toString()
                high.text = coinWithValues.values[0].high_1d.toString()
                low.text = coinWithValues.values[0].low_1d.toString()
                dialog.show()
            }
        }
    }

    override fun getItemCount(): Int {
        return if (coinWithValuesList == null) {
            0
        } else {
            coinWithValuesList!!.size
        }
    }

    class CoinsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var currencyName: TextView = itemView.findViewById(R.id.currencyName)
        var current: TextView = itemView.findViewById(R.id.current)
        var high: TextView = itemView.findViewById(R.id.high_1d)
        var low: TextView = itemView.findViewById(R.id.low_1d)
    }
}
