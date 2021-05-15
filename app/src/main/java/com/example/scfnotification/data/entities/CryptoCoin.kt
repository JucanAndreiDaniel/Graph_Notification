package com.example.scfnotification.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cryptoCoins")
class CryptoCoin constructor(
    id: String,
    symbol: String,
    name: String,
    image: String,
    lastUpdated: String,
) {

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String = id

    @ColumnInfo(name = "name")
    val name: String = name

    @ColumnInfo(name = "symbol")
    val symbol: String = symbol

    @ColumnInfo(name = "image")
    val image: String = image

    @ColumnInfo(name = "lastUpdated")
    val lastUpdated: String = lastUpdated

    @ColumnInfo(name = "favorite")
    var favorite: Boolean = false
}
