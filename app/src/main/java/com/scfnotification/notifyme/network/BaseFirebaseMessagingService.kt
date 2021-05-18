package com.scfnotification.notifyme.network

import com.google.firebase.messaging.FirebaseMessagingService
import com.scfnotification.notifyme.data.sharedpreferences.IPreferenceHelper
import com.scfnotification.notifyme.data.sharedpreferences.PreferenceManager

open class BaseFirebaseMessagingService: FirebaseMessagingService() {
    private val preferenceHelper: IPreferenceHelper by lazy { PreferenceManager(this) }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        preferenceHelper.setFireBaseKey(token)
    }
}