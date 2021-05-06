package com.example.scfnotification.data
import android.app.Application
import com.example.scfnotification.data.entities.CryptoCoin
import java.sql.Timestamp


class RoomApplication : Application() {

    override fun onCreate() {
        super.onCreate()

            val database = AppDatabase.getInstance(context = this@RoomApplication)

            if (database.CryptoCoinDao().all.isEmpty()) {
                val cryptoCoins: MutableList<CryptoCoin> = mutableListOf()
                    val coin = CryptoCoin(id="bitcoin",symbol = "btc",name="Bitcoin",image="",lastUpdated ="")
                    cryptoCoins.add(coin)
                database.CryptoCoinDao().insertAll(cryptoCoins = cryptoCoins)
            }
    }

}
