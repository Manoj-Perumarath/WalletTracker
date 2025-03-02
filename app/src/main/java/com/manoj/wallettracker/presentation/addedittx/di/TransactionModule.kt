package com.manoj.wallettracker.presentation.addedittx.di

import com.manoj.wallettracker.data.ui.addedittx.repo.TransactionRepo
import com.manoj.wallettracker.data.ui.addedittx.repoimpl.TransactionRepoImpl
import com.manoj.wallettracker.data.ui.home.repo.HomeRepo
import com.manoj.wallettracker.data.ui.home.repoimpl.HomeRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class TransactionModule {

    @Binds
    abstract fun provideTransactionRepo(
        transactionRepoImpl: TransactionRepoImpl
    ): TransactionRepo

}
