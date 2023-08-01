package com.example.listofbooks.room

import com.example.listofbooks.room.dao.BookDao

class LocalDataSourceImpl(private val bookDao: BookDao) : LocalDataSource {


    override suspend fun insertBooks(books: List<BookEntity>) {
        bookDao.insertAll(books)
    }

    override suspend fun getNumberOfBooks(): Int {
        return bookDao.getNumberOfBooks()
    }

    override suspend fun getBooks(): List<BookEntity> {
        return bookDao.getAllBooks()
    }
}