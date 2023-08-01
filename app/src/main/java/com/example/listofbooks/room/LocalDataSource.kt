package com.example.listofbooks.room

import Books

interface LocalDataSource {

    suspend fun insertBooks(books: List<BookEntity>)

    suspend fun getNumberOfBooks(): Int

    suspend fun getBooks():List<BookEntity>
}