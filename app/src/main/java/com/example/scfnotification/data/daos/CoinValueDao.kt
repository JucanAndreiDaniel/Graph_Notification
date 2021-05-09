package com.example.scfnotification.data.daos

import androidx.room.*
import com.example.scfnotification.data.entities.CoinValue

@Dao
interface CoinValueDao {

    @Query("SELECT * FROM CoinValue WHERE coin=:coin AND currency=:currency")
    fun findbyCoin(coin: String, currency: String): CoinValue

    @Insert
    fun insert(CoinValue:CoinValue)

    @Insert
    fun insertAll(CoinValues: List<CoinValue>)

    @Update
    fun update(CoinValue: CoinValue)

    @Delete
    fun delete(CoinValue: CoinValue)

}