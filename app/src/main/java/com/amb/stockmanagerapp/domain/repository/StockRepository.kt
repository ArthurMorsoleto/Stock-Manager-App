package com.amb.stockmanagerapp.domain.repository

import com.amb.stockmanagerapp.data.dto.ProductResponse
import com.amb.stockmanagerapp.domain.model.Product

interface StockRepository {
    suspend fun getStockProducts(): List<ProductResponse>
    suspend fun getProductDetails(id: Int): ProductResponse?
    suspend fun saveProduct(product: Product)
}