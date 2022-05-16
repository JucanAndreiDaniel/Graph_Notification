package com.scfnotification.notifyme.ui.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scfnotification.notifyme.R
import com.scfnotification.notifyme.data.adapters.NotificationAdapter
import com.scfnotification.notifyme.data.entities.CoinAndNotification
import com.scfnotification.notifyme.data.sharedpreferences.IPreferenceHelper
import com.scfnotification.notifyme.data.sharedpreferences.PreferenceManager
import com.scfnotification.notifyme.ui.viewmodels.NotificationsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NotificationAdapter
    private val preferenceHelper: IPreferenceHelper by lazy { PreferenceManager(this.requireContext()) }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        lateinit var currentContext: Context
        notificationsViewModel = ViewModelProvider(this)[NotificationsViewModel::class.java]
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        if (container != null) {
            currentContext = container.context
        }

        val userNotifTextView: TextView = root.findViewById(R.id.userNotificationsTV)
        val username = preferenceHelper.getUsername()
        if (username.endsWith('s') || username.endsWith('x') || username.endsWith('z'))
            userNotifTextView.text = "$username' Notifications"
        else
            userNotifTextView.text = "$username's Notifications"

        adapter = NotificationAdapter()
        recyclerView = root.findViewById(R.id.fragment_notification_recyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(currentContext)
        showNotifications(adapter, currentContext)

        val nameList: MutableList<String> = mutableListOf()
        val coinIDList: MutableList<Int> = mutableListOf()
        notificationsViewModel.getNames().observe(
            viewLifecycleOwner
        ) {
            if (it != null) {
                for (item in it) {
                    nameList += item.coin.coin_id.replaceFirstChar { it1 ->
                        if (it1.isLowerCase()) it1.titlecase(
                            Locale.ENGLISH
                        ) else it1.toString()
                    }
                    coinIDList += item.coin.id
                }
            }
        }
        val button: Button = root.findViewById(R.id.fragment_notification_addNotification)
        button.setOnClickListener { showDialog(nameList,coinIDList) }
        return root
    }

    private fun showDialog(nameList: List<String>, coinIDList: MutableList<Int>) {
        val directions =
            NotificationsFragmentDirections.actionNavigationNotificationsToCreateNotificationDialog(
                nameList.toTypedArray(),
                coinIDList.toIntArray()
            )
        view?.findNavController()?.navigate(directions)
    }

    private fun showNotifications(adapter: NotificationAdapter, context: Context) {
        notificationsViewModel.getNotification(context).observe(
            viewLifecycleOwner
        ) {
            if (it != null) {
                adapter.submitList(it as MutableList<CoinAndNotification>?)
            }
        }
    }
}
