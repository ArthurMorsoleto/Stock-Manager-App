package com.amb.stockmanagerapp.data.repository

import com.amb.stockmanagerapp.data.source.dto.ProductDTO
import com.amb.stockmanagerapp.domain.repository.StockRepository
import kotlinx.coroutines.delay

class StockRepositoryImpl : StockRepository {

    override suspend fun getStockProducts(): List<ProductDTO> {
        delay(3000)
        return fakeProducts
    }

    private val fakeProducts = listOf(
        ProductDTO(
            id = "id01",
            name = "nome do produto 1",
            description = "descrição do produto 1",
            image = "image 1",
            price = 5.0,
            quantity = 3
        ),
        ProductDTO(
            id = "id02",
            name = "nome do produto 3",
            description = "descrição do produto 3",
            image = "image 2",
            price = 5.0,
            quantity = 3
        ),
        ProductDTO(
            id = "id03",
            name = "nome do produto 3",
            description = "descrição do produto 3",
            image = "image 3",
            price = 5.0,
            quantity = 3
        )
    )
}