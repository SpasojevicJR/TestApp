package com.example.testapp.api

import com.example.testapp.models.Products
import retrofit2.http.GET

interface ProductsApi {

    @GET("/api/v1/products.json?brand=maybelline")
    suspend fun getProducts() : Array<Products> = arrayOf()

}