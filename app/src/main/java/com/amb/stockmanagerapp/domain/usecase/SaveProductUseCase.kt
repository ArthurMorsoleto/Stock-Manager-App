package com.amb.stockmanagerapp.domain.usecase

import com.amb.stockmanagerapp.domain.model.Product
import com.amb.stockmanagerapp.domain.repository.StockRepository
import javax.inject.Inject

class SaveProductUseCase @Inject constructor(
    private val repository: StockRepository
) {
    suspend operator fun invoke(product: Product) {
        repository.saveProduct(product)
    }
}