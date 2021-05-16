package com.example.scfnotification.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.scfnotification.data.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: Repository,
) : ViewModel() {

    val coinId: String = savedStateHandle.get<String>(COIN_ID_SAVED_STATE_KEY)!!

    val coin = repository.getCoin(coinId).asLiveData()

    val isFav = repository.getFav(coinId).asLiveData().toString().isEmpty()

    fun favorite(coinId: String) {
        repository.setFavorite(coinId)
    }

    companion object {
        private const val COIN_ID_SAVED_STATE_KEY = "coinId"
    }
}
