package com.example.scfnotification.ui.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.*
import com.example.scfnotification.data.entities.CryptoCoin
import com.example.scfnotification.data.repositories.CryptoCoinRepository
import kotlinx.coroutines.launch

class FavouritesViewModel(private val repository: CryptoCoinRepository) : ViewModel() {

    val allCryptocoins : LiveData<List<CryptoCoin>> by lazy { repository.allCryptocoins.asLiveData() }

    fun insert(cryptoCoin: CryptoCoin) = viewModelScope.launch {
        repository.insert(cryptoCoin)
    }
}

class FavoriteViewModelFactory(private val repository: CryptoCoinRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavouritesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavouritesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
