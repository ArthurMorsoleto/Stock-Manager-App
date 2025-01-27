package com.amb.stockmanagerapp.domain.repository

import com.amb.stockmanagerapp.data.dto.ProductResponse

interface StockRepository {
    suspend fun getStockProducts(): List<ProductResponse>
    suspend fun getProductDetails(id: Int): ProductResponse?
}