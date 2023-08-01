package com.example.listofbooks

import Books
import com.example.listofbooks.api.ApiGetService
import retrofit2.Response

class NetworkDataSourceImpl(private val apiService: ApiGetService) : NetworkDataSource {

    override suspend fun getBooks(): Response<List<Books>> {
        return apiService.getPost()
    }
}