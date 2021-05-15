package com.example.scfnotification.network

data class CryptoCoinValueItem(
    val ath: Double,
    val ath_time: String,
    val atl: Double,
    val atl_time: String,
    val coin_id: String,
    val current: Double,
    val high_1d: Double,
    val image: String,
    val last_updated: String,
    val low_1d: Double,
    val name: String,
    val symbol: String
)
