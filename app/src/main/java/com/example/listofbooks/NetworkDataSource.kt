package com.example.listofbooks

import Books
import retrofit2.Response

interface NetworkDataSource {

    suspend fun getBooks(): Response<List<Books>>
}