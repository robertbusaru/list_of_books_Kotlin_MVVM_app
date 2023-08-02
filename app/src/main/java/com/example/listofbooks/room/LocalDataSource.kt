package com.example.listofbooks.room

interface LocalDataSource {

    suspend fun insertBooks(books: List<BookEntity>)

    suspend fun getNumberOfBooks(): Int

    suspend fun getBooks():List<BookEntity>
}