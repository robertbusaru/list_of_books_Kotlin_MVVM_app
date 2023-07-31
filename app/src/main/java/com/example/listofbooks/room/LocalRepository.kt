package com.example.listofbooks.room

import com.example.listofbooks.room.dao.BookDao
import kotlinx.coroutines.flow.Flow

class LocalRepository(private val bookDao: BookDao) {

    val allBooks: List<BookEntity> = bookDao.getAllBooks()

    suspend fun insertBooks(books: List<BookEntity>) {
        bookDao.insertAll(books)
    }

}