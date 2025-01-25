package com.amb.stockmanagerapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.amb.stockmanagerapp.data.dto.LocalProductResponse

@Database(entities = [LocalProductResponse::class], version = 1, exportSchema = false)
abstract class ProductsDataBase : RoomDatabase() {
    abstract fun productsDao(): ProductsDao
}