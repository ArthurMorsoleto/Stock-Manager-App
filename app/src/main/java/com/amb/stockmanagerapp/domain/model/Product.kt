package com.amb.stockmanagerapp.domain.model

data class Product(
    val description: String,
    val id: Int,
    val image: String,
    val name: String,
    val rating: Rating,
    val price: Double
)

data class Rating(
    val count: Int,
    val rate: Double
)
