package com.example.scfnotification.data
import android.app.Application
import com.example.scfnotification.data.repositories.CryptoCoinRepository
import com.example.scfnotification.ui.favourites.FavouritesViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module


class RoomApplication : Application() {

    private val cryptoModule = module {
        factory { AppDatabase.getDatabase(RoomApplication()).CryptoCoinDao() }
        factory { CryptoCoinRepository(get()) }
        viewModel { FavouritesViewModel(get()) }
    }
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@RoomApplication)
            modules(cryptoModule)
        }
    }

}
