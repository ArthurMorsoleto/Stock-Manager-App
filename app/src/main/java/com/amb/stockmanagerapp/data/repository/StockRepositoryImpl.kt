package com.amb.stockmanagerapp.data.repository

import com.amb.stockmanagerapp.data.dto.ProductResponse
import com.amb.stockmanagerapp.data.dto.mapToLocalProductResponse
import com.amb.stockmanagerapp.data.dto.mapToProduct
import com.amb.stockmanagerapp.data.local.ProductsDao
import com.amb.stockmanagerapp.data.remote.ProductsApi
import com.amb.stockmanagerapp.domain.repository.StockRepository
import javax.inject.Inject

class StockRepositoryImpl @Inject constructor(
    private val remoteDataSource: ProductsApi,
    private val localDataSource: ProductsDao,
    private val shouldFetchDataFromRemoteDataSource: Boolean = false
) : StockRepository {

    override suspend fun getStockProducts(): List<ProductResponse> {
        var response = localDataSource.getAll().map { it.mapToProduct() }
        if (response.isEmpty() || shouldFetchDataFromRemoteDataSource) {
            response = remoteDataSource.getProducts()
            localDataSource.upsertAll(response.map { it.mapToLocalProductResponse() })
        }
        return response
    }
}