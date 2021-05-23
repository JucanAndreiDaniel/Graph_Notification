package com.scfnotification.notifyme.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.scfnotification.notifyme.data.daos.*
import com.scfnotification.notifyme.data.entities.CoinValue
import com.scfnotification.notifyme.data.entities.CryptoCoin
import com.scfnotification.notifyme.data.entities.Notification

@Database(
    entities = [(CryptoCoin::class), (CoinValue::class), (Notification::class)],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun CryptoCoinDao(): CryptoCoinDao
    abstract fun CoinValueDao(): CoinValueDao
    abstract fun CoinWithValuesDao(): CoinWithValuesDao
    abstract fun NotificationDao(): NotificationDao
    abstract fun CoinAndNotificationDao(): CoinAndNotificationDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        private const val DB_NAME = "coins.db"

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DB_NAME
                ).fallbackToDestructiveMigration()
//                    .addCallback(DatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME).build()
        }
    }
}
