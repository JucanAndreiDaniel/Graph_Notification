package com.example.scfnotification.data.daos

import androidx.room.*
import com.example.scfnotification.data.entities.CoinValue
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinValueDao {

    @Query("SELECT * FROM CoinValue LIMIT :limit")
    fun valueLimit(limit: Int): Flow<List<CoinValue>>

    @Query("SELECT * FROM CoinValue WHERE coin=:coin AND currency=:currency")
    fun findByCoin(coin: String, currency: String): CoinValue

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(CoinValue: CoinValue)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(CoinValues: List<CoinValue>)

    @Update
    fun update(CoinValue: CoinValue)

    @Delete
    fun delete(CoinValue: CoinValue)

    @Query("DELETE FROM CoinValue")
    fun deleteAll()
}
