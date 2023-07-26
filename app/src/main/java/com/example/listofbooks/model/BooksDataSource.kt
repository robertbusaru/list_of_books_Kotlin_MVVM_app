package com.example.listofbooks.model

import com.example.listofbooks.Books

interface BooksDataSource {
    suspend fun getBooksData(): ApiResult<List<Books>>
}