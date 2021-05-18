package com.scfnotification.notifyme.ui.details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.scfnotification.notifyme.data.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
        Log.d("Fav", "add $coinId to fav")
        viewModelScope.launch(Dispatchers.IO) {
            repository.setFavorite(coinId)
        }
        Log.d("FavResult", "added $coinId to fav")
    }

    companion object {
        private const val COIN_ID_SAVED_STATE_KEY = "coinId"
    }
}
