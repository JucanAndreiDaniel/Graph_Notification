package com.scfnotification.notifyme.ui.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.scfnotification.notifyme.R
import com.scfnotification.notifyme.data.entities.Notification
import com.scfnotification.notifyme.ui.viewmodels.NotificationsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateNotificationDialog : DialogFragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private lateinit var optionsCardView: CardView
    private lateinit var coinCardView: CardView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        isCancelable = true
        val args: CreateNotificationDialogArgs by navArgs()
        val nameList: List<String> = args.nameList.toList()
        val coinList: List<Int> = args.coinIDList.toList()
        val root = inflater.inflate(R.layout.dialog_createnotification, container, false)
        notificationsViewModel = ViewModelProvider(this)[NotificationsViewModel::class.java]
        val button: Button = root.findViewById(R.id.save_notification)

        optionsCardView = root.findViewById(R.id.value_type_cv)
        coinCardView = root.findViewById(R.id.coin_cv)

        coinCardView.setOnClickListener { showCoinmenu(coinCardView, nameList) }

        optionsCardView.setOnClickListener {
            showOptionMenu(optionsCardView)
        }

        button.setOnClickListener {
            val coin: TextView = root.findViewById(R.id.cn_coin)
            val option: TextView = root.findViewById(R.id.cn_value_type)
            val value: EditText = root.findViewById(R.id.cn_value)
            val doubleValue = value.text.toString().toDouble()
            val coinID = coinList[nameList.indexOf(coin.text.toString())]
            val notification = Notification(
                coinID,
                option.text.toString(),
                0.0,
                doubleValue,
                true,
                false
            )

            context?.let { it1 -> notificationsViewModel.setNotification(notification, it1) }
            dismiss()
        }
        return root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        setStyle(STYLE_NO_TITLE, R.style.AlertDialogCustom)
        return dialog
    }

    private fun showCoinmenu(view: View, nameList: List<String>) {
        val contextStyle = ContextThemeWrapper(this.context, R.style.popupMenuStyle)
        val popupMenu = PopupMenu(contextStyle, view)
        for (name in nameList) {
            popupMenu.menu.add(name)
        }

        val coin: TextView = view.findViewById(R.id.cn_coin)
        popupMenu.show()
        popupMenu.setOnMenuItemClickListener { item: MenuItem? ->
            if (item != null) {
                coin.text = item.title
            }
            true
        }
    }

    private fun showOptionMenu(view: View) {

        val contextStyle = ContextThemeWrapper(this.context, R.style.popupMenuStyle)
        val popupMenu = PopupMenu(contextStyle, view)
        popupMenu.inflate(R.menu.notifications_options_type)
        popupMenu.show()
        val option: TextView = view.findViewById(R.id.cn_value_type)
        popupMenu.setOnMenuItemClickListener { item: MenuItem? ->

            when (item!!.itemId) {
                R.id.val_bigger -> {
                    option.text = "Value Bigger than"
                }

                R.id.val_lower -> {
                    option.text = "Value Lower than"
                }

                R.id.val_equal -> {
                    option.text = "Value Equal to"
                }

                R.id.val_g_perc -> {
                    option.text = "Growth in Percentage"
                }

                R.id.val_d_perc -> {
                    option.text = "Decrease in percentage"
                }
            }

            true
        }
    }
}
