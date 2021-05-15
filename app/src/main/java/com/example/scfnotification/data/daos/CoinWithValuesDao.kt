package com.example.scfnotification.data.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.scfnotification.data.entities.CoinWithValues
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinWithValuesDao {
    @Transaction
    @Query("SELECT * FROM cryptoCoins")
    fun getCoinsWithValues(): Flow<List<CoinWithValues>>

    @Transaction
    @Query("SELECT * FROM cryptoCoins LIMIT :limit")
    fun getCoinslimit(limit: Int): Flow<List<CoinWithValues>>

    @Transaction
    @Query("SELECT * FROM cryptoCoins WHERE Favorite=:fav")
    fun getFavorites(fav: Boolean): Flow<List<CoinWithValues>>

    @Transaction
    @Query("SELECT * FROM cryptoCoins WHERE name LIKE :stringToFilter")
    fun getFilteredCoins(stringToFilter: String): Flow<List<CoinWithValues>>

    @Transaction
    @Query("SELECT * FROM cryptoCoins WHERE name=:name")
    fun getCoin(name: String): Flow<CoinWithValues>
}
