package com.manoj.wallettracker.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.manoj.wallettracker.data.room.converters.DateConverter
import com.manoj.wallettracker.data.room.dao.WalletTrackerDao
import com.manoj.wallettracker.data.room.model.TransactionDb
import kotlinx.coroutines.DelicateCoroutinesApi

@Database(
    entities = [
        TransactionDb::class,
    ],
    version = 1,
)
@TypeConverters(DateConverter::class)
abstract class WalletTrackerDatabase : RoomDatabase() {
    abstract fun walletTrackerDao(): WalletTrackerDao

    @DelicateCoroutinesApi
    companion object {
        private const val DB_NAME = "wallet-tracker"

        @Volatile
        private var INSTANCE: WalletTrackerDatabase? = null

        fun getInstance(context: Context): WalletTrackerDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): WalletTrackerDatabase {
            val db = Room.databaseBuilder(
                context,
                WalletTrackerDatabase::class.java,
                DB_NAME
            )
                .fallbackToDestructiveMigration()

            return db.build()
        }
    }
}