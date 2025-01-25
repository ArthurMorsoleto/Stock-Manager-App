package com.amb.stockmanagerapp.data.repository

import com.amb.stockmanagerapp.data.remote.ProductsApi
import com.amb.stockmanagerapp.data.source.dto.ProductResponse
import com.amb.stockmanagerapp.domain.repository.StockRepository

class StockRepositoryImpl(
    private val api: ProductsApi
) : StockRepository {

    override suspend fun getStockProducts(): List<ProductResponse> {
        return api.getProducts()
    }
}