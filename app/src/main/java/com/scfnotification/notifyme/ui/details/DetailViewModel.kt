package com.scfnotification.notifyme.ui.details

import android.content.Context
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.scfnotification.notifyme.data.repositories.Repository
import com.scfnotification.notifyme.data.sharedpreferences.IPreferenceHelper
import com.scfnotification.notifyme.data.sharedpreferences.PreferenceManager
import com.scfnotification.notifyme.network.NetworkOperations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: Repository
) : ViewModel() {

    val coinId: String = savedStateHandle.get<String>(COIN_ID_SAVED_STATE_KEY)!!

    val coin = repository.getCoin(coinId).asLiveData()

    val isFav = repository.getFav(coinId).asLiveData()

    fun showFav() {
        Log.d("checkFav", isFav.value.toString())
    }

    fun favorite(coinId: String, context: Context) {
        val preferenceHelper: IPreferenceHelper by lazy { PreferenceManager(context) }

        Log.d("Fav", "add $coinId to fav")
        viewModelScope.launch(Dispatchers.IO) {
            repository.setFavorite(coinId)
            NetworkOperations().addFavorite(coinId, preferenceHelper.getApiKey())
        }
        Log.d("FavResult", "added $coinId to fav")
    }

    companion object {
        private const val COIN_ID_SAVED_STATE_KEY = "coinId"
    }
}
