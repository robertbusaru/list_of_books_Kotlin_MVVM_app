package com.example.listofbooks.room

import Books
import android.os.Handler
import android.os.Looper
import com.example.listofbooks.room.dao.BookDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LocalRepository(private val bookDao: BookDao) {

    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    fun getAllBooks(callback: (List<BookEntity>) -> Unit) {
        coroutineScope.launch {
            val books = bookDao.getAllBooks()
            Handler(Looper.getMainLooper()).post {
                callback(books)
            }
        }
    }

    suspend fun insertBooks(books: List<Books>) {
        withContext(Dispatchers.IO) {
            val bookEntities = books.map { book ->
                BookEntity(
                    bookImage = book.bookImage,
                    bookName = book.bookName,
                    authorImage = book.authorImage,
                    authorName = book.authorName,
                    isbn = book.isbn,
                    bookTypeImage = book.bookTypeImage,
                    bookType = book.bookType,
                    favorite = book.favorite,
                    description = book.description
                )
            }
            bookDao.insertAll(bookEntities)
        }
    }

    suspend fun getNumberOfBooks(): Int {
        return bookDao.getNumberOfBooks()
    }

}
