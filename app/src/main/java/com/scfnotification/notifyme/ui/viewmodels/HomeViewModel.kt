package com.scfnotification.notifyme.ui.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.scfnotification.notifyme.data.entities.CoinWithValues
import com.scfnotification.notifyme.data.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val getCoins = repository.getCoinDetails().asLiveData()

    fun update(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            Repository.updateDB(context)
            Repository.updateNotifications(context)
        }
    }

    fun filter(stringToFilter: String): LiveData<List<CoinWithValues>> {
        return repository.filter(stringToFilter).asLiveData()
    }
}
