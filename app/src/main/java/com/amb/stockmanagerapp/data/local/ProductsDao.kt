package com.amb.stockmanagerapp.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.amb.stockmanagerapp.data.dto.LocalProductResponse

@Dao
interface ProductsDao {

    @Query("SELECT * FROM productResponse")
    suspend fun getAll(): List<LocalProductResponse>

    @Upsert
    suspend fun upsert(product: LocalProductResponse)

    @Upsert
    suspend fun upsertAll(products: List<LocalProductResponse>)

    @Query("DELETE FROM productresponse WHERE id = :id")
    suspend fun deleteById(id: String): Int
}