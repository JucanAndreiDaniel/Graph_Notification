package com.example.scfnotification.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.scfnotification.R
import com.example.scfnotification.data.sharedpreferences.IPreferenceHelper
import com.example.scfnotification.data.sharedpreferences.PreferenceManager
import com.example.scfnotification.ui.login.LoginActivity


class SettingsFragment : Fragment() {

    private lateinit var logoutButton: Button
    private lateinit var notificationCardView: CardView
    private val preferenceHelper: IPreferenceHelper by lazy { PreferenceManager(this.requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_settings, container, false)

        logoutButton = root.findViewById(R.id.logoutButton)
        logoutButton.setOnClickListener { logout() }

        notificationCardView = root.findViewById(R.id.notificationSettingCardView)
        notificationCardView.setOnClickListener { showNotificationSettingMenu(notificationCardView) }

        return root
    }

    private fun logout() {
        preferenceHelper.clearApiKey()
        startActivity(Intent(this.context, LoginActivity::class.java))
    }

    private fun showNotificationSettingMenu(view: View) {

        val contextStyle = ContextThemeWrapper(this.context, R.style.popupMenuStyle)
        val popupMenu = PopupMenu(contextStyle, view)
        popupMenu.inflate(R.menu.notifications_frequency_menu)
        popupMenu.show()

        popupMenu.setOnMenuItemClickListener { item: MenuItem? ->

            when (item!!.itemId) {
                R.id.five_minutes -> {
                    preferenceHelper.setNotificationFrequency(5)
                    Toast.makeText(
                        activity?.applicationContext,
                        "Notification Frequency set to 5 minutes",
                        Toast.LENGTH_LONG
                    ).show()
                }

                R.id.ten_minutes -> {
                    preferenceHelper.setNotificationFrequency(10)
                    Toast.makeText(
                        activity?.applicationContext,
                        "Notification Frequency set to 10 minutes",
                        Toast.LENGTH_LONG
                    ).show()
                }

                R.id.fifteen_minutes -> {
                    preferenceHelper.setNotificationFrequency(15)
                    Toast.makeText(
                        activity?.applicationContext,
                        "Notification Frequency set to 15 minutes",
                        Toast.LENGTH_LONG
                    ).show()
                }

                R.id.thirty_minutes -> {
                    preferenceHelper.setNotificationFrequency(30)
                    Toast.makeText(
                        activity?.applicationContext,
                        "Notification Frequency set to 30 minutes",
                        Toast.LENGTH_LONG
                    ).show()
                }

                R.id.sixty_minutes -> {
                    preferenceHelper.setNotificationFrequency(60)
                    Toast.makeText(
                        activity?.applicationContext,
                        "Notification Frequency set to 60 minutes",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            true
        }
    }
}
