package com.scfnotification.notifyme.data.entities

import android.annotation.SuppressLint
import androidx.room.* // ktlint-disable no-wildcard-imports

@SuppressLint("RestrictedApi")
@Entity(
    foreignKeys = [
        ForeignKey(
            entity = CryptoCoin::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("coin"),
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = arrayOf("coin"))]
)
data class CoinValue(
    @PrimaryKey @ColumnInfo(name = "coin_currency") var coin_currency: String,
    @ColumnInfo(name = "coin") var coin: Int,
    @ColumnInfo(name = "coin_id") val coin_id: String,
    @ColumnInfo(name = "currency") var currency: String,
    @ColumnInfo(name = "current") var current: Double,
    @ColumnInfo(name = "high_1d") var high_1d: Double,
    @ColumnInfo(name = "low_1d") var low_1d: Double,
    @ColumnInfo(name = "ath") var ath: Double,
    @ColumnInfo(name = "atl") var atl: Double,
    @ColumnInfo(name = "ath_time") var ath_time: String,
    @ColumnInfo(name = "atl_time") var atl_time: String
)
