package com.example.listofbooks.model

import Books

interface BooksDataSource {
    suspend fun getBooksData(): ApiResult<List<Books>>
}