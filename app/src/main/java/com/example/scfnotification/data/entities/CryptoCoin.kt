package com.example.scfnotification.data.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.net.URL
import java.sql.Timestamp


@Entity(tableName = "cryptoCoins")
class CryptoCoin constructor(id:String, symbol:String, name:String, image:String, lastUpdated:String) {

    @PrimaryKey
    var id: String = id
    @ColumnInfo(name = "name")
    val name: String = name
    @ColumnInfo(name = "symbol")
    val symbol: String = symbol
    @ColumnInfo(name = "image")
    val image: String = image
    @ColumnInfo(name = "lastUpdated")
    val lastUpdated: String = lastUpdated
}
