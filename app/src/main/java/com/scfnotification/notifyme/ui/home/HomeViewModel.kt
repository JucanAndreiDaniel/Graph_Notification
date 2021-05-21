package com.scfnotification.notifyme.ui.home

import android.content.Context
import androidx.lifecycle.*
import com.scfnotification.notifyme.data.entities.CoinWithValues
import com.scfnotification.notifyme.data.repositories.Repository
import com.scfnotification.notifyme.ui.details.DetailViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: Repository
) : ViewModel() {

    val getCoins = repository.getCoinDetails().asLiveData()

    fun update(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            Repository.updateDB(context)
        }
    }

    fun filter(stringToFilter: String): LiveData<List<CoinWithValues>> {
        return repository.filter(stringToFilter).asLiveData()
    }

    companion object {
        private const val COIN_ID_SAVED_STATE_KEY = "coinId"
    }
}
