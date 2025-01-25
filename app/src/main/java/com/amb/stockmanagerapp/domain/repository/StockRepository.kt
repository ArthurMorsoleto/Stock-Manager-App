package com.amb.stockmanagerapp.domain.repository

import com.amb.stockmanagerapp.data.source.dto.ProductResponse

interface StockRepository {
    suspend fun getStockProducts(): List<ProductResponse>
}