package com.scfnotification.notifyme.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cryptoCoins")
data class CryptoCoin constructor(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "coin_id") val coin_id: String,
    @ColumnInfo(name = "symbol") val symbol: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "lastUpdated") val lastUpdated: String,
    @ColumnInfo(name = "favorite") var favorite: Boolean = false,
)
