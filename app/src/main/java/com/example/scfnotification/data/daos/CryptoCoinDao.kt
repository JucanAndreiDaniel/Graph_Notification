package com.example.scfnotification.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.scfnotification.data.entities.CryptoCoin


@Dao
interface CryptoCoinDao {

    @get:Query("SELECT * FROM cryptoCoins")
    val all: List<CryptoCoin>

    @Insert
    fun insertAll(cryptoCoins: List<CryptoCoin>)

    @Insert
    fun insert(CryptoCoin: CryptoCoin)

    @Delete
    fun delete(CryptoCoin: CryptoCoin)
}