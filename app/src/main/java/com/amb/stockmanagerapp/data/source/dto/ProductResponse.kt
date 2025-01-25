package com.amb.stockmanagerapp.data.source.dto

import com.amb.stockmanagerapp.domain.model.Product
import com.amb.stockmanagerapp.domain.model.Rating
import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("category")
    val category: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("rating")
    val rating: RatingResponse,
    @SerializedName("title")
    val title: String
)

data class RatingResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("rate")
    val rate: Double
)

fun ProductResponse.mapToProduct(): Product {
    return Product(
        id = id,
        name = title,
        price = price,
        description = description,
        image = image,
        rating = Rating(count = rating.count, rate = rating.rate)
    )
}
