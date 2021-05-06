package com.example.scfnotification.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.scfnotification.data.entities.CoinValue


@Dao
interface CoinValueDao {
    @get:Query("SELECT * FROM CoinValue")
    val all: List<CoinValue>

    @Query("SELECT * FROM CoinValue WHERE coin_currency IN (:CoinValueIds)")
    fun loadAllByIds(CoinValueIds: IntArray): List<CoinValue>

    @Query("SELECT * FROM CoinValue WHERE coin_currency LIKE :coin_currency LIMIT 1")
    fun findByName(coin_currency: String): CoinValue

    @Insert
    fun insertAll(CoinValues: List<CoinValue>)

    @Delete
    fun delete(CoinValue: CoinValue)

}