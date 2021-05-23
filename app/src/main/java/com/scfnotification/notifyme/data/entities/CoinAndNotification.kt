package com.scfnotification.notifyme.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class CoinAndNotification(
    @Embedded val notification: Notification,
    @Relation(
        parentColumn = "coin_id",
        entityColumn = "id"
    )
    val coin: CryptoCoin,
)
