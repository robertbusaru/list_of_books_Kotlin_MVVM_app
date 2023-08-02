package com.example.listofbooks.model

import com.example.listofbooks.api.ApiGetService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RestApiClient {

    val apiClient: ApiGetService by lazy {
        val retrofit: Retrofit = Retrofit.Builder().baseUrl("https://www.jsonkeeper.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        return@lazy retrofit.create(
            ApiGetService::class.java
        )
    }

}