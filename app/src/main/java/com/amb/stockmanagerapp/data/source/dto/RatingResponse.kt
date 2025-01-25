package com.amb.stockmanagerapp.data.source.dto

import com.google.gson.annotations.SerializedName

data class RatingResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("rate")
    val rate: Double
)