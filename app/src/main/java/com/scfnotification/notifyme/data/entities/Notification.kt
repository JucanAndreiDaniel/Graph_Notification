package com.scfnotification.notifyme.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notifications")
class Notification constructor(
    @PrimaryKey @ColumnInfo(name = "coin_id") var coin_id: String,
    @ColumnInfo(name = "value_type") var value_type: String,
    @ColumnInfo(name = "initial_value") var initial_value: Double,
    @ColumnInfo(name = "final_value") var final_value: Double,
    @ColumnInfo(name = "enabled") var enabled: Boolean,
    @ColumnInfo(name = "via_email") var via_mail: Boolean,
)
