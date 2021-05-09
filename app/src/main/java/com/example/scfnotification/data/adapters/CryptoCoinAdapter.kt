package com.example.scfnotification.data.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.scfnotification.R
import com.example.scfnotification.data.entities.CryptoCoin

class CryptoCoinAdapter(ct: Context) : ListAdapter<CryptoCoin, CryptoCoinAdapter.CryptoCoinHolder>(CryptoCoinsComparator()) {

    private var context: Context = ct

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoCoinHolder {
        val inflater: LayoutInflater = LayoutInflater.from(context)

        return CryptoCoinHolder(inflater.inflate(R.layout.row_layout, parent, false))
    }

    override fun onBindViewHolder(holder: CryptoCoinHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.name)
    }

    class CryptoCoinHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val wordItemView: TextView = itemView.findViewById(R.id.textView)

        fun bind(text: String?) {
            wordItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): CryptoCoinHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return CryptoCoinHolder(view)
            }
        }
    }

    class CryptoCoinsComparator : DiffUtil.ItemCallback<CryptoCoin>() {
        override fun areItemsTheSame(oldItem: CryptoCoin, newItem: CryptoCoin): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: CryptoCoin, newItem: CryptoCoin): Boolean {
            return oldItem.name == newItem.name
        }
    }
}