package com.example.scfnotification.data.entities

import android.annotation.SuppressLint
import androidx.room.*
import java.sql.Timestamp

@SuppressLint("RestrictedApi")
@Entity(foreignKeys = [
    ForeignKey(
        entity = CryptoCoin::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("coin"),
        onDelete = ForeignKey.CASCADE
    )
],
    indices = [Index(value = arrayOf("coin"))]
)
class CoinValue constructor(coin_currency: String, coin: String, currency: String,current :Float,high_1d:Float, low_1d:Float, ath:Float, atl:Float ,ath_time: String, atl_time:String) {

    @PrimaryKey
    var coin_currency: String = coin_currency
    @ColumnInfo(name = "coin")
    var coin: String = coin
    @ColumnInfo(name = "currency")
    var currency:String = currency
    @ColumnInfo(name = "current")
    var current:Float =current
    @ColumnInfo(name = "high_1d")
    var high_1d:Float =high_1d
    @ColumnInfo(name = "low_1d")
    var low_1d:Float =low_1d
    @ColumnInfo(name = "ath")

    var ath:Float =ath
    @ColumnInfo(name = "ath_time")
    var ath_time: String = ath_time

    @ColumnInfo(name = "atl")
    var atl:Float =atl
    @ColumnInfo(name = "atl_time")
    var atl_time:String = atl_time
}