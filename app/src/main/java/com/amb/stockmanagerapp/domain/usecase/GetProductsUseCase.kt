package com.amb.stockmanagerapp.domain.usecase

import com.amb.stockmanagerapp.data.source.dto.mapToProduct
import com.amb.stockmanagerapp.domain.model.Product
import com.amb.stockmanagerapp.domain.repository.StockRepository
import com.amb.stockmanagerapp.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: StockRepository
) {
    suspend operator fun invoke(): Flow<Response<List<Product>>> {
        return flow {
            try {
                emit(Response.Loading())
                val products = repository.getStockProducts().map { it.mapToProduct() }
                emit(Response.Success(products))
            } catch (e: Exception) {
                emit(Response.Error(e.localizedMessage ?: "error when fetching products"))
            }
        }
    }
}