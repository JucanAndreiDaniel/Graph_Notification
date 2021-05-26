package com.scfnotification.notifyme.data.adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.scfnotification.notifyme.R
import com.scfnotification.notifyme.data.entities.CoinAndNotification
import com.scfnotification.notifyme.data.entities.Notification
import com.scfnotification.notifyme.data.repositories.Repository
import com.scfnotification.notifyme.data.sharedpreferences.IPreferenceHelper
import com.scfnotification.notifyme.data.sharedpreferences.PreferenceManager
import com.scfnotification.notifyme.databinding.NotificationRowLayoutBinding
import com.scfnotification.notifyme.network.NetworkOperations
import com.scfnotification.notifyme.ui.fragments.NotificationsFragmentDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import kotlin.math.roundToLong

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
        init {
            val preferenceHelper: IPreferenceHelper by lazy { PreferenceManager(binding.switch1.context) }

            binding.clickableNotificationCard.setOnClickListener {
                binding.coinandnotification?.let { it1 ->
                    val direction =
                        NotificationsFragmentDirections.actionNavigationNotificationsToModifyNotificationDialog(
                            it1.coin.name,
                            it1.notification.value_type,
                            it1.notification.final_value.roundToLong()
                        )
                    it.findNavController().navigate(direction)
                }
            }

            binding.switch1.setOnClickListener {
                binding.coinandnotification?.let { it1 ->
                    NetworkOperations().changeEnabled(
                        preferenceHelper.getApiKey(),
                        it1.coin.id,
                        !it1.notification.enabled
                    )
                    it1.notification.enabled = !it1.notification.enabled
                }
            }

            binding.deleteNotification.setOnClickListener {
                binding.coinandnotification?.let { it1 ->
                    val builder =
                        AlertDialog.Builder(
                            binding.deleteNotification.context,
                            R.style.AlertDialogCustom
                        )
                    builder.setTitle("Delete Notification")
                    builder.setMessage("Do you want to delete " + it1.coin.name + " notification?")

                    builder.setPositiveButton("Yes") { _, _ ->
                        NetworkOperations().deleteNotification(
                            preferenceHelper.getApiKey(),
                            it1.coin.id
                        )
                        runBlocking {
                            deleteNoti(
                                binding.deleteNotification.context,
                                it1.notification
                            )
                        }
                    }

                    builder.setNegativeButton("No") { _, _ ->
                    }
                    builder.show()
                }
            }
        }

        suspend fun deleteNoti(context: Context, notification: Notification) {
            withContext(Dispatchers.IO) {
                Repository.deleteNotifications(context, notification)
            }
        }

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
