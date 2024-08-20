package com.example.catapi.data.api

import com.example.catapi.data.response.ListCatResponse
import retrofit2.http.GET
import retrofit2.http.Header


internal interface CatApi {
    @GET("v1/images/search?limit=10")
    suspend fun fetchListCatResponse(@Header("x-api-chave") apiKey: String): List<ListCatResponse>
}