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
import com.scfnotification.notifyme.databinding.DialogModifynotificationBinding
import com.scfnotification.notifyme.ui.viewmodels.NotificationsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ModifyNotificationDialog : DialogFragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private lateinit var optionsCardView: CardView
    private lateinit var coinCardView: CardView
    private lateinit var binding: DialogModifynotificationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        isCancelable = true
        val args: ModifyNotificationDialogArgs by navArgs()
        binding = DialogModifynotificationBinding.inflate(inflater, container, false)
        notificationsViewModel = ViewModelProvider(this).get(NotificationsViewModel::class.java)
        val button: Button = binding.saveNotification

        optionsCardView = binding.valueTypeCv
        coinCardView = binding.coinCv

        optionsCardView.setOnClickListener {
            showOptionMenu(optionsCardView)
        }
        val coin: TextView = binding.cnCoin
        val option: TextView = binding.cnValueType

        coin.text = args.coinId
        option.text = args.valueType
        button.setOnClickListener {
            val value: EditText = binding.cnValue
            val doubleValue = value.text.toString().toDouble()
            val coinId = coin.text.toString().lowercase()
            val notification = Notification(
                coinId,
                option.text.toString(),
                0.0,
                doubleValue,
                true,
                false
            )

            context?.let { it1 -> notificationsViewModel.setNotification(notification, it1) }
            dismiss()
        }
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.AlertDialogCustom)
        return dialog
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
            }

            true
        }
    }
}
