package com.amb.stockmanagerapp.domain.repository

import com.amb.stockmanagerapp.data.source.dto.ProductDTO

interface StockRepository {
    suspend fun getStockProducts(): List<ProductDTO>
}