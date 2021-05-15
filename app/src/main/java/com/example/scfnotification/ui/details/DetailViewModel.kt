package com.example.scfnotification.ui.details

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.scfnotification.data.entities.CoinWithValues
import com.example.scfnotification.data.entities.CryptoCoin
import com.example.scfnotification.data.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: Repository,
) : ViewModel() {

//    val coinID: String = savedStateHandle.get<String>(COIN_ID_SAVED_STATE_KEY)!!

    fun coin(coin: CoinWithValues): LiveData<CoinWithValues> {
        return repository.getCoin(coin.coin.id).asLiveData()
    }

    fun favorite(context: Context, coin: CryptoCoin) {
        repository.setFavorite(context, coin)
    }

    companion object {
        private const val COIN_ID_SAVED_STATE_KEY = "coinID"
    }
}
