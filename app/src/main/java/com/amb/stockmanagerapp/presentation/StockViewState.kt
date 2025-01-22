package com.amb.stockmanagerapp.presentation

import com.amb.stockmanagerapp.domain.model.Product

data class StockViewState(
    val isLoading: Boolean = true,
    val data: List<Product> = emptyList(),
    val error: String = ""
)
