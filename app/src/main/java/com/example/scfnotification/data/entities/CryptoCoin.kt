package com.example.scfnotification.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cryptoCoins")
data class CryptoCoin constructor(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "symbol") val symbol: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "lastUpdated") val lastUpdated: String,
    @ColumnInfo(name = "favorite") var favorite: Boolean = false,
)
