package com.scfnotification.notifyme.data.repositories

import android.content.Context
import com.scfnotification.notifyme.data.AppDatabase
import com.scfnotification.notifyme.data.daos.CoinWithValuesDao
import com.scfnotification.notifyme.data.entities.CoinAndNotification
import com.scfnotification.notifyme.data.entities.CoinValue
import com.scfnotification.notifyme.data.entities.CryptoCoin
import com.scfnotification.notifyme.data.entities.Notification
import com.scfnotification.notifyme.data.sharedpreferences.IPreferenceHelper
import com.scfnotification.notifyme.data.sharedpreferences.PreferenceManager
import com.scfnotification.notifyme.network.CryptoCoinValueItem
import com.scfnotification.notifyme.network.NetworkOperations
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val coinWithValues: CoinWithValuesDao
) {

    fun getFavorites() = coinWithValues.getFavorites(true)

    fun getFav(string: String) = coinWithValues.getFavorite(string)

    fun getCoinDetails() = coinWithValues.getCoinslimit(100)

    fun filter(stringToFilter: String) = coinWithValues.getFilteredCoins(stringToFilter)

    fun setFavorite(coinId: String) {
        coinWithValues.setFav(coinId)
    }

    fun updateFavorite(context: Context) {
        val preferenceHelper: IPreferenceHelper by lazy { PreferenceManager(context) }
        val favs: List<CryptoCoinValueItem> =
            NetworkOperations().getFavorites(preferenceHelper.getApiKey())
        for (item in favs) {
            setFavorite(item.coin_id)
        }
    }

    fun getCoin(coinId: String) = coinWithValues.getCoin(coinId)

    companion object {

        private var coinDatabase: AppDatabase? = null

        private fun initializeDB(context: Context): AppDatabase {
            return AppDatabase.getDatabase(context)
        }

        fun setNotification(context: Context, notification: Notification) {
            val preferenceHelper: IPreferenceHelper by lazy { PreferenceManager(context) }
            NetworkOperations().setNotification(
                preferenceHelper.getApiKey(),
                notification.coin_id,
                notification.value_type,
                notification.final_value,
                notification.via_mail
            )
            coinDatabase = initializeDB(context)
            coinDatabase!!.NotificationDao().insert(notification)
        }

        fun getNotifications(context: Context): Flow<List<CoinAndNotification>> {
            coinDatabase = initializeDB(context)
            return coinDatabase!!.CoinAndNotificationDao().all()
        }

        fun updateNotifications(context: Context) {
            val preferenceHelper: IPreferenceHelper by lazy { PreferenceManager(context) }
            val data: List<Notification> =
                NetworkOperations().getNotifications(preferenceHelper.getApiKey())
            coinDatabase = initializeDB(context)
            coinDatabase!!.NotificationDao().deleteAll()
            coinDatabase!!.NotificationDao().insertAll(data)
        }

        fun updateDB(context: Context) {
            val preferenceHelper: IPreferenceHelper by lazy { PreferenceManager(context) }
            coinDatabase = initializeDB(context)
            val data: List<CryptoCoinValueItem> =
                NetworkOperations().getcoins(preferenceHelper.getApiKey())
            val coins: MutableList<CryptoCoin> = mutableListOf()
            val values: MutableList<CoinValue> = mutableListOf()
            for (item in data) {
                val coin =
                    CryptoCoin(
                        id = item.coin_id,
                        name = item.name,
                        image = item.image,
                        symbol = item.symbol,
                        lastUpdated = item.last_updated,
                    )
                val value = CoinValue(
                    coin = item.coin_id,
                    ath = item.ath,
                    atl = item.atl,
                    ath_time = item.ath_time,
                    atl_time = item.atl_time,
                    current = item.current,
                    currency = "usd",
                    coin_currency = "usd_" + item.coin_id,
                    high_1d = item.high_1d,
                    low_1d = item.low_1d
                )
                coins += coin
                values += value
            }
            if (preferenceHelper.checkFirstRun() == "Yes") {
                coinDatabase!!.CryptoCoinDao().insertAll(coins)
                coinDatabase!!.CoinValueDao().insertAll(values)
                preferenceHelper.setFirstRun("No")
            } else {
                coinDatabase!!.CryptoCoinDao().updateAll(coins)
                coinDatabase!!.CoinValueDao().updateAll(values)
            }
        }
    }
}
