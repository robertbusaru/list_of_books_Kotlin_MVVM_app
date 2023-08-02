package com.example.listofbooks.room

import com.example.listofbooks.Books
import com.example.listofbooks.mapBooksToBookEntity
import com.example.listofbooks.room.dao.BookDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalRepository(private val bookDao: BookDao) {


    suspend fun insertBooks(books: List<Books>) {
        withContext(Dispatchers.IO) {
            bookDao.insertAll(books.mapBooksToBookEntity())
        }
    }

    suspend fun getNumberOfBooks(): Int {
        return bookDao.getNumberOfBooks()
    }

}
