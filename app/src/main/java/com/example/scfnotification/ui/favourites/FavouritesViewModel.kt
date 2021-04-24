package com.example.scfnotification.ui.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FavouritesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Here you will find your favourite stock/crypto currencies"
    }
    val text: LiveData<String> = _text
}