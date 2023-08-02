package com.example.listofbooks

import Books
import com.example.listofbooks.model.ApiResult
import retrofit2.Response

interface NetworkDataSource {

    suspend fun getBooks(): Response<List<Books>>
}