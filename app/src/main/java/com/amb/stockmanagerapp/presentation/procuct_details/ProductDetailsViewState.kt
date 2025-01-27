package com.amb.stockmanagerapp.presentation.procuct_details

import com.amb.stockmanagerapp.domain.model.Product

data class ProductDetailsViewState(
    val isLoading: Boolean = true,
    val data: Product? = null,
    val error: String? = null
)