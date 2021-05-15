package com.example.scfnotification.data.adapters

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.scfnotification.R
import com.example.scfnotification.network.CryptoCoinValueItem

class BaseRecyclerAdapter(ct: Context) :
    RecyclerView.Adapter<BaseRecyclerAdapter.BaseViewHolder>() {

    private var coinList: List<CryptoCoinValueItem> = listOf()
    private var context: Context = ct

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(context)

        return BaseViewHolder(inflater.inflate(R.layout.row_layout, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.currencyName.text = coinList[position].name
        holder.current.text = coinList[position].current.toString()
        holder.high_1d.text = coinList[position].high_1d.toString()
        holder.low_1d.text = coinList[position].low_1d.toString()
        holder.itemView.setOnClickListener {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.details_fragment)
            val coin_name: TextView = dialog.findViewById(R.id.coin_name)
            val current_price: TextView = dialog.findViewById(R.id.current_price)
            val high: TextView = dialog.findViewById(R.id.high)
            val low: TextView = dialog.findViewById(R.id.low)
            coin_name.text = coinList[holder.adapterPosition].name
            current_price.text = coinList[holder.adapterPosition].current.toString()
            high.text = coinList[holder.adapterPosition].high_1d.toString()
            low.text = coinList[holder.adapterPosition].low_1d.toString()
            dialog.show()
        }
    }

    override fun getItemCount(): Int {
        return coinList.size
    }

    fun setCoinListItems(coinListz: List<CryptoCoinValueItem>) {
        this.coinList = coinListz
        notifyDataSetChanged()
    }

    class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var currencyName: TextView = itemView.findViewById(R.id.currencyName)
        var current: TextView = itemView.findViewById(R.id.current)
        var high_1d: TextView = itemView.findViewById(R.id.high_1d)
        var low_1d: TextView = itemView.findViewById(R.id.low_1d)
    }
}
