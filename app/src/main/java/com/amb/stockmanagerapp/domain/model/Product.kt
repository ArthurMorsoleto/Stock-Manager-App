package com.amb.stockmanagerapp.domain.model

data class Product(
    val description: String = "",
    val id: Int = 0,
    val image: String = "",
    val name: String = "",
    val rating: Rating = Rating(),
    val price: Double = 0.0
)

data class Rating(
    val count: Int = 0,
    val rate: Double = 0.0
)
