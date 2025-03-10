package com.amb.stockmanagerapp.data.remote

import com.amb.stockmanagerapp.data.dto.ProductResponse
import retrofit2.http.GET

interface ProductsApi {

    @GET("/products")
    suspend fun getProducts(): List<ProductResponse>
}