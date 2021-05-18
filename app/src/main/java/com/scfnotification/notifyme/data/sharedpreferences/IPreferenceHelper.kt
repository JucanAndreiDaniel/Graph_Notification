package com.scfnotification.notifyme.data.sharedpreferences

interface IPreferenceHelper {
    fun setApiKey(apiKey: String)
    fun getApiKey(): String
    fun clearApiKey()

    fun setNotificationFrequency(frequency: Int)
    fun getNotificationFrequency(): Int

    fun setUsername(username: String)
    fun getUsername(): String
    fun clearUsername()

    fun clearPrefs()
}
