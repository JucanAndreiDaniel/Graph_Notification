package com.scfnotification.notifyme.ui.notifications

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.scfnotification.notifyme.data.entities.CoinAndNotification
import com.scfnotification.notifyme.data.entities.Notification
import com.scfnotification.notifyme.data.repositories.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationsViewModel : ViewModel() {

    fun getNotification(context: Context): LiveData<List<CoinAndNotification>> {
        return Repository.getNotifications(context).asLiveData()
    }

    fun setNotification(notification: Notification, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            Repository.setNotification(context, notification)
        }
    }
}
