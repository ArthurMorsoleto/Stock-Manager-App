package com.amb.stockmanagerapp.data.source.dto

import com.google.gson.annotations.SerializedName

data class ProductDTO(
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("valor")
    val price: Double
)