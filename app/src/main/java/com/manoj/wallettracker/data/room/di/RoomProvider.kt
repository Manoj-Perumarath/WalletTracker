package com.manoj.wallettracker.data.room.di

import android.content.Context
import com.manoj.wallettracker.data.room.WalletTrackerDatabase
import com.manoj.wallettracker.data.room.dao.WalletTrackerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.DelicateCoroutinesApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WalletDatabaseModule {

    @OptIn(DelicateCoroutinesApi::class)
    @Singleton
    @Provides
    fun provideWalletDao(@ApplicationContext context: Context): WalletTrackerDao {
        return WalletTrackerDatabase.getInstance(context)
            .walletTrackerDao()
    }

}
