package com.example.scfnotification.data.daos

import androidx.lifecycle.LiveData
import androidx.room.* // ktlint-disable no-wildcard-imports
import com.example.scfnotification.data.entities.CryptoCoin
import kotlinx.coroutines.flow.Flow

@Dao
interface CryptoCoinDao {

    @Query("SELECT * FROM cryptoCoins")
    fun all(): Flow<List<CryptoCoin>>

    @Query("SELECT * FROM cryptoCoins LIMIT :limit")
    fun coinsLimit(limit: Int): Flow<List<CryptoCoin>>

    @Query("SELECT * FROM cryptoCoins WHERE name=:name")
    fun findByName(name: String): LiveData<CryptoCoin>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(cryptoCoins: List<CryptoCoin>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(CryptoCoin: CryptoCoin)

    @Update
    fun update(CryptoCoin: CryptoCoin)

    @Update
    fun updateAll(cryptoCoins: List<CryptoCoin>)

    @Delete
    fun delete(CryptoCoin: CryptoCoin)

    @Query("DELETE FROM cryptoCoins")
    fun deleteAll()

    @Query("UPDATE cryptoCoins SET favorite = 1 WHERE id =:coinId")
    fun setFav(coinId: String)
}
