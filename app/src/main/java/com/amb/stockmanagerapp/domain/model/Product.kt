package com.amb.stockmanagerapp.domain.model

data class Product(
    val description: String,
    val id: Int,
    val image: String,
    val name: String,
    val quantity: Int,
    val price: Double
)
