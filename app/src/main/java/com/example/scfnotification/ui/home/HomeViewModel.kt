package com.example.scfnotification.ui.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.scfnotification.data.entities.CoinWithValues
import com.example.scfnotification.data.repositories.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private var liveDataCoins: Flow<List<CoinWithValues>>? = null

    fun getCoins(context: Context): LiveData<List<CoinWithValues>> {
        liveDataCoins = Repository.getCoinDetails(context)
        return liveDataCoins!!.asLiveData()
    }

    fun update(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            Repository.updateDB(context)
        }
    }

    fun filter(context: Context, stringToFilter: String): LiveData<List<CoinWithValues>> {
        liveDataCoins = Repository.filter(context, "%$stringToFilter%")
        return liveDataCoins!!.asLiveData()
    }
}
