package com.example.listofbooks.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.listofbooks.room.BookEntity

@Dao
interface BookDao {
    @Insert
    fun insertAll(books: List<BookEntity>)

    @Query("SELECT * FROM books_table")
    fun getAllBooks(): List<BookEntity>

    @Query("SELECT COUNT(id) FROM books_table")
    suspend fun getNumberOfBooks(): Int

    @Query("DELETE FROM books_table")
    suspend fun deleteFromTable()

//    @Query("SELECT COUNT(id) FROM books_table WHERE ")

}
