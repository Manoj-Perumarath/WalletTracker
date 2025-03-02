package com.manoj.wallettracker.presentation.splash.di

import com.manoj.wallettracker.data.ui.splash.repo.SplashRepository
import com.manoj.wallettracker.data.ui.splash.repoimpl.SplashRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class SplashModule {

    @Binds
    abstract fun provideSplashRepository(
        splashRepositoryImpl: SplashRepositoryImpl
    ): SplashRepository

}
