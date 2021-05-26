package com.scfnotification.notifyme.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.scfnotification.notifyme.R
import com.scfnotification.notifyme.data.sharedpreferences.IPreferenceHelper
import com.scfnotification.notifyme.data.sharedpreferences.PreferenceManager
import com.scfnotification.notifyme.ui.activities.LoginActivity

class SettingsFragment : Fragment() {

    private lateinit var logoutButton: Button
    private lateinit var usernameWelcome: TextView
    private val preferenceHelper: IPreferenceHelper by lazy { PreferenceManager(this.requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_settings, container, false)

        usernameWelcome = root.findViewById(R.id.welcomeUserTV)
        ("Hello, " + preferenceHelper.getUsername()).also { usernameWelcome.text = it }

        logoutButton = root.findViewById(R.id.logoutButton)
        logoutButton.setOnClickListener { logout() }
        return root
    }

    private fun logout() {
        preferenceHelper.clearApiKey()
        preferenceHelper.clearUsername()
        startActivity(Intent(this.context, LoginActivity::class.java))
    }
}
