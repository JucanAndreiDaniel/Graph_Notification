package com.scfnotification.notifyme.data.daos

import androidx.room.*
import com.scfnotification.notifyme.data.entities.CoinAndNotification
import com.scfnotification.notifyme.data.entities.Notification
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinAndNotificationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(Notification: Notification)

    @Transaction
    @Query("SELECT * FROM notifications")
    fun all(): Flow<List<CoinAndNotification>>
}
