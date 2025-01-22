package com.amb.stockmanagerapp.di

import com.amb.stockmanagerapp.data.repository.StockRepositoryImpl
import com.amb.stockmanagerapp.domain.repository.StockRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object StockModule {

    @Provides
    fun provideStockRepository(): StockRepository {
        return StockRepositoryImpl()
    }
}