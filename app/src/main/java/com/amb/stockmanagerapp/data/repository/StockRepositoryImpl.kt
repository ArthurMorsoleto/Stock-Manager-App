package com.amb.stockmanagerapp.data.repository

import com.amb.stockmanagerapp.data.dto.ProductResponse
import com.amb.stockmanagerapp.data.dto.mapToLocalProductResponse
import com.amb.stockmanagerapp.data.dto.mapToProduct
import com.amb.stockmanagerapp.data.local.ProductsDao
import com.amb.stockmanagerapp.data.remote.ProductsApi
import com.amb.stockmanagerapp.di.IODispatcher
import com.amb.stockmanagerapp.domain.model.Product
import com.amb.stockmanagerapp.domain.repository.StockRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StockRepositoryImpl @Inject constructor(
    private val remoteDataSource: ProductsApi,
    private val localDataSource: ProductsDao,
    private val shouldFetchDataFromRemoteDataSource: Boolean = false,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : StockRepository {

    override suspend fun getStockProducts(): List<ProductResponse> {
        return withContext(dispatcher) {
            var response = localDataSource.getAll().map { it.mapToProduct() }
            if (response.isEmpty() || shouldFetchDataFromRemoteDataSource) {
                response = remoteDataSource.getProducts()
                localDataSource.upsertAll(response.map { it.mapToLocalProductResponse() })
            }
            response
        }
    }

    override suspend fun getProductDetails(id: Int): ProductResponse? {
        return withContext(dispatcher) {
            localDataSource.getById(id)?.mapToProduct()
        }
    }

    override suspend fun saveProduct(product: Product) {
        withContext(dispatcher) {
            // TODO create mapper fun to LocalProductResponse
            // localDataSource.upsert(product)
        }
    }
}