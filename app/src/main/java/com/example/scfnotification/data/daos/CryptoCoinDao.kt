package com.example.scfnotification.data.daos

import androidx.room.*
import com.example.scfnotification.data.entities.CryptoCoin
import kotlinx.coroutines.flow.Flow


@Dao
interface CryptoCoinDao {

    @Query("SELECT * FROM cryptoCoins")
    fun all(): Flow<List<CryptoCoin>>

    @Query("SELECT * FROM cryptoCoins WHERE name=:name")
    fun findByName(name: String): CryptoCoin

    @Insert
    fun insertAll(cryptoCoins: List<CryptoCoin>)

    @Insert
    fun insert(CryptoCoin: CryptoCoin)

    @Update
    fun update(CryptoCoin: CryptoCoin)

    @Delete
    fun delete(CryptoCoin: CryptoCoin)
}