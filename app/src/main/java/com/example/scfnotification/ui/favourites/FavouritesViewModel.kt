package com.example.scfnotification.ui.favourites

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.scfnotification.data.entities.CoinWithValues
import com.example.scfnotification.data.repositories.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FavouritesViewModel : ViewModel() {

    private var liveDataCoins: Flow<List<CoinWithValues>>? = null

    fun getFavorites(context: Context): LiveData<List<CoinWithValues>> {
        liveDataCoins = Repository.getFavs(context)
        return liveDataCoins!!.asLiveData()
    }

    fun update(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            Repository.updateDB(context)
        }
    }
}
