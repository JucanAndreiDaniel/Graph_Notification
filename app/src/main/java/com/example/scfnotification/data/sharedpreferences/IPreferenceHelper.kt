package com.example.scfnotification.data.sharedpreferences

interface IPreferenceHelper {
    fun setApiKey(apiKey: String)
    fun getApiKey(): String
    fun clearPrefs()
}