package com.scfnotification.notifyme.data.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.scfnotification.notifyme.data.entities.CoinAndNotification
import com.scfnotification.notifyme.databinding.NotificationRowLayoutBinding
import java.math.BigDecimal

class NotificationAdapter :
    ListAdapter<CoinAndNotification, RecyclerView.ViewHolder>(NotificationDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NotificationViewHolder(
            NotificationRowLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    private fun setRoundedNumber(number: BigDecimal): BigDecimal {
        return if (number.equals(number.toInt()))
            number
        else
            number.setScale(2, BigDecimal.ROUND_CEILING)
    }

    override fun submitList(list: MutableList<CoinAndNotification>?) {
        if (list != null) {
            for (element in list) {
                element.notification.initial_value =
                    setRoundedNumber(BigDecimal.valueOf(element.notification.initial_value)).toDouble()
                element.notification.final_value =
                    setRoundedNumber(BigDecimal.valueOf(element.notification.final_value)).toDouble()
            }
        }
        super.submitList(list)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val notification = getItem(position)
        (holder as NotificationViewHolder).bind(notification)
    }

    class NotificationViewHolder(
        private val binding: NotificationRowLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CoinAndNotification) {
            binding.apply {
                coinandnotification = item
                executePendingBindings()
            }
        }
    }
}

private class NotificationDiffCallback : DiffUtil.ItemCallback<CoinAndNotification>() {

    override fun areItemsTheSame(
        oldItem: CoinAndNotification,
        newItem: CoinAndNotification
    ): Boolean {
        return oldItem.coin.id == newItem.coin.id
    }

    override fun areContentsTheSame(
        oldItem: CoinAndNotification,
        newItem: CoinAndNotification
    ): Boolean {
        return oldItem == newItem
    }
}
