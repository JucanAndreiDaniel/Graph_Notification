package com.scfnotification.notifyme.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class CoinWithValues(
    @Embedded
    val coin: CryptoCoin,

    @Relation(
        parentColumn = "id",
        entityColumn = "coin"
    )
    val values: List<CoinValue>
)
