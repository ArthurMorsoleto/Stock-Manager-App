package com.amb.stockmanagerapp.data.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "productResponse")
data class LocalProductResponse(
    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "category")
    val category: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "image")
    @SerializedName("image")
    val image: String,

    @ColumnInfo(name = "price")
    val price: Double,

    @ColumnInfo(name = "rate_count")
    val rateCount: Int,

    @ColumnInfo(name = "rate")
    val rate: Double,

    @ColumnInfo(name = "title")
    val title: String
)

fun LocalProductResponse.mapToProduct(): ProductResponse {
    return ProductResponse(
        id = id,
        title = title,
        price = price,
        description = description,
        image = image,
        category = category,
        rating = RatingResponse(count = rateCount, rate = rate)
    )
}