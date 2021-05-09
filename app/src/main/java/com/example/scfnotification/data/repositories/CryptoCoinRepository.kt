package com.example.scfnotification.data.repositories

import androidx.annotation.WorkerThread
import com.example.scfnotification.data.daos.CryptoCoinDao
import com.example.scfnotification.data.entities.CryptoCoin
import kotlinx.coroutines.flow.Flow

class CryptoCoinRepository(private val cryptoCoinDao: CryptoCoinDao) {
    val allCryptocoins: Flow<List<CryptoCoin>> = cryptoCoinDao.all()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(cryptoCoin: CryptoCoin){
        cryptoCoinDao.insert(cryptoCoin)
    }
}