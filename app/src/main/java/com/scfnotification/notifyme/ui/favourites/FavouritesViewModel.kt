package com.scfnotification.notifyme.ui.favourites

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.scfnotification.notifyme.data.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    val getFavorites = repository.getFavorites().asLiveData()

    fun update(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
//            Repository.updateDB(context)
            repository.updateFavorite(context)
        }
    }
}
