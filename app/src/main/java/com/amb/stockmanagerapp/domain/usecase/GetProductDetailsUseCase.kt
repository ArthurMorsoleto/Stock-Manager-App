package com.amb.stockmanagerapp.domain.usecase

import com.amb.stockmanagerapp.data.dto.mapToProduct
import com.amb.stockmanagerapp.domain.model.Product
import com.amb.stockmanagerapp.domain.repository.StockRepository
import com.amb.stockmanagerapp.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetProductDetailsUseCase @Inject constructor(
    private val repository: StockRepository
) {
    suspend operator fun invoke(id: String): Flow<Response<Product>> {
        return flow {
            try {
                emit(Response.Loading())
                val product = repository.getProductDetails(id.toInt())?.mapToProduct()
                if (product == null) {
                    emit(Response.Error("error fetching product"))
                } else {
                    emit(Response.Success(product))
                }
            } catch (e: Exception) {
                emit(Response.Error(e.localizedMessage ?: "error fetching product"))
            }
        }
    }
}