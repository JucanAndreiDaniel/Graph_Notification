package com.scfnotification.notifyme.data.daos

import androidx.room.*
import com.scfnotification.notifyme.data.entities.Notification

@Dao
interface NotificationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(Notification: Notification)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(Notifications: List<Notification>)

    @Update
    fun update(Notification: Notification)

    @Update
    fun updateAll(Notifications: List<Notification>)

    @Delete
    fun delete(Notification: Notification)

    @Query("DELETE FROM notifications")
    fun deleteAll()
}
