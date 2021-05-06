package com.example.scfnotification.data.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView;
import com.example.scfnotification.R

class BaseRecyclerAdapter(ct: Context, stringArray: Array<String>) :
        RecyclerView.Adapter<BaseRecyclerAdapter.BaseViewHolder>() {

    private var baseStringArray: Array<String> = stringArray
    private var context: Context = ct

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(context)

        return BaseViewHolder(inflater.inflate(R.layout.row_layout, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.textView.text = baseStringArray[position]
    }

    override fun getItemCount(): Int {
        return baseStringArray.size
    }

    class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView.findViewById(R.id.currencyName)
    }
}