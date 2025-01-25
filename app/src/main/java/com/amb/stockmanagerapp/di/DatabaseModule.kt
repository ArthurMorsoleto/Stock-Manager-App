package com.amb.stockmanagerapp.di

import android.content.Context
import androidx.room.Room
import com.amb.stockmanagerapp.data.local.ProductsDao
import com.amb.stockmanagerapp.data.local.ProductsDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): ProductsDataBase {
        return Room.databaseBuilder(
            context.applicationContext,
            ProductsDataBase::class.java,
            "products.db"
        ).build()
    }

    @Provides
    fun provideProductsDao(database: ProductsDataBase): ProductsDao = database.productsDao()
}