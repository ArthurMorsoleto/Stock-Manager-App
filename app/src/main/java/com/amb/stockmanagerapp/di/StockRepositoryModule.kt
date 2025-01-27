package com.amb.stockmanagerapp.di

import com.amb.stockmanagerapp.data.local.ProductsDao
import com.amb.stockmanagerapp.data.remote.ProductsApi
import com.amb.stockmanagerapp.data.repository.StockRepositoryImpl
import com.amb.stockmanagerapp.domain.repository.StockRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object StockRepositoryModule {

    @Provides
    fun provideStockRepository(
        remoteDataSource: ProductsApi,
        localDataSource: ProductsDao
    ): StockRepository {
        return StockRepositoryImpl(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource,
            shouldFetchDataFromRemoteDataSource = false,
            dispatcher = Dispatchers.IO
        )
    }
}