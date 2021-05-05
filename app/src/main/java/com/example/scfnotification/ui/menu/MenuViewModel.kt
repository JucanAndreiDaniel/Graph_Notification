package com.example.scfnotification.ui.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MenuViewModel: ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Menu Placeholder"
    }
    val text: LiveData<String> = _text
}
