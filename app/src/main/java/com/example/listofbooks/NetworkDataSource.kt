package com.example.listofbooks

import retrofit2.Response

interface NetworkDataSource {

    suspend fun getBooks(): Response<List<Books>>
}