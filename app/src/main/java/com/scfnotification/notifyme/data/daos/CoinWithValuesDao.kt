package com.scfnotification.notifyme.data.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.scfnotification.notifyme.data.entities.CoinWithValues
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
    @Query("SELECT * FROM cryptoCoins WHERE name LIKE '%' || :stringToFilter || '%' ")
    fun getFilteredCoins(stringToFilter: String): Flow<List<CoinWithValues>>

    @Transaction
    @Query("SELECT * FROM cryptoCoins WHERE id=:coinId")
    fun getCoin(coinId: String): Flow<CoinWithValues>

    @Transaction
    @Query("UPDATE cryptoCoins SET favorite = 1 WHERE id =:coinId")
    fun setFav(coinId: String)

    @Transaction
    @Query("SELECT EXISTS(SELECT 1 FROM cryptoCoins where id=:coinId AND favorite = 1 LIMIT 1)")
    fun getFavorite(coinId: String): Flow<Boolean>
}
