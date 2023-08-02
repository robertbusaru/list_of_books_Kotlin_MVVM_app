package com.example.listofbooks.room

import android.util.Log
import com.example.listofbooks.room.dao.BookDao

class LocalDataSourceImpl(private val bookDao: BookDao) : LocalDataSource {


    override suspend fun insertBooks(books: List<BookEntity>) {
        Log.d("Network_data_source","insertAll "+ books.size)
        bookDao.insertAll(books)
    }

    override suspend fun getNumberOfBooks(): Int {
        return bookDao.getNumberOfBooks()
    }

    override suspend fun getBooks(): List<BookEntity> {
        return bookDao.getAllBooks()
    }
}