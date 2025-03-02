package com.manoj.wallettracker.foundation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {

    @Provides
    @Named(BACKGROUND)
    fun provideDispatcherIo(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Provides
    @Named(MAIN_THREAD)
    fun provideDispatcherMain(): CoroutineDispatcher {
        return Dispatchers.Main
    }

    const val BACKGROUND = "background"
    const val MAIN_THREAD = "main_thread"
}
