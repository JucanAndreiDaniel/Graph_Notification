package com.example.scfnotification.ui.favourites

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.scfnotification.data.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    repository: Repository,
) : ViewModel() {

    val getFavorites = repository.getFavs().asLiveData()

    fun update(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            Repository.updateDB(context)
        }
    }
}
