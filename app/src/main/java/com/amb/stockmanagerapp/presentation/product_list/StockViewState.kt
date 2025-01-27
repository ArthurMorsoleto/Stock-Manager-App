package com.amb.stockmanagerapp.presentation.product_list

import com.amb.stockmanagerapp.domain.model.Product

data class StockViewState(
    val isLoading: Boolean = true,
    val data: List<Product> = emptyList(),
    val error: String = ""
)
