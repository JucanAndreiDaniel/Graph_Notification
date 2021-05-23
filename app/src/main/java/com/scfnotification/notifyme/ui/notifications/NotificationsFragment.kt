package com.scfnotification.notifyme.ui.notifications

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scfnotification.notifyme.R
import com.scfnotification.notifyme.data.adapters.NotificationAdapter
import com.scfnotification.notifyme.data.entities.CoinAndNotification

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NotificationAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        lateinit var currentContext: Context
        notificationsViewModel = ViewModelProvider(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        if (container != null) {
            currentContext = container.context
        }

        adapter = NotificationAdapter()
        recyclerView = root.findViewById(R.id.fragment_notification_recyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(currentContext)
        showNotifications(adapter, currentContext)
        val button: Button = root.findViewById(R.id.fragment_notification_addNotification)
        button.setOnClickListener { showDialog() }
        return root
    }

    private fun showDialog() {
        val directions =
            NotificationsFragmentDirections.actionNavigationNotificationsToCreateNotificationDialog()
        view?.findNavController()?.navigate(directions)
    }

    private fun showNotifications(adapter: NotificationAdapter, context: Context) {
        notificationsViewModel.getNotification(context).observe(
            viewLifecycleOwner,
            {
                if (it != null) {
                    adapter.submitList(it as MutableList<CoinAndNotification>?)
                }
            }
        )
    }
}
