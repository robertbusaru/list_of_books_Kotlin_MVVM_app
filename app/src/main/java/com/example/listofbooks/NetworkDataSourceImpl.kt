package com.example.listofbooks

import Books
import com.example.listofbooks.api.ApiGetService
import com.example.listofbooks.model.ApiResult
import com.example.listofbooks.room.BookEntity
import retrofit2.Response

class NetworkDataSourceImpl(private val apiService: ApiGetService) : NetworkDataSource {

    override suspend fun getBooks(): Response<List<Books>> {
        return apiService.getPost()
    }
}