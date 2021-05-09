package com.example.scfnotification.data



import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.scfnotification.data.daos.CoinValueDao
import com.example.scfnotification.data.daos.CryptoCoinDao
import com.example.scfnotification.data.entities.CoinValue
import com.example.scfnotification.data.entities.CryptoCoin


@Database(entities = [(CryptoCoin::class), (CoinValue::class)], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun CryptoCoinDao(): CryptoCoinDao

    abstract fun CoinValueDao(): CoinValueDao

    companion object {

        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) { INSTANCE ?: buildDatabase(context).also { INSTANCE = it } }

        private fun buildDatabase(ctx: Context) =
            Room.databaseBuilder(ctx.applicationContext, AppDatabase::class.java, "word_database")
                .build()
    }

}
