package com.manoj.wallettracker.presentation.home.di

import com.manoj.wallettracker.data.ui.home.repo.HomeRepo
import com.manoj.wallettracker.data.ui.home.repoimpl.HomeRepoImpl
import com.manoj.wallettracker.data.ui.splash.repo.SplashRepository
import com.manoj.wallettracker.data.ui.splash.repoimpl.SplashRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class HomeModule {

    @Binds
    abstract fun provideHomeRepo(
        homeRepoImpl: HomeRepoImpl
    ): HomeRepo

}
