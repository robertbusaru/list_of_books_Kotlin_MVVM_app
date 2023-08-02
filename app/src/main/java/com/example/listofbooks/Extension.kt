package com.example.listofbooks

import com.example.listofbooks.room.BookEntity

fun List<Books>.mapBooksToBookEntity() : List<BookEntity> {
    val list: ArrayList<BookEntity> = ArrayList()
    this.forEach {
        list.add(
            BookEntity(
                0,
                it.bookImage,
                it.bookName,
                it.authorImage,
                it.authorName,
                it.isbn,
                it.bookTypeImage,
                it.bookType,
                it.favorite,
                it.description,
            )
        )
    }
    return list
}

fun List<BookEntity>.mapBookEntityToBooks() : List<Books> {
    val list: ArrayList<Books> = ArrayList()
    this.forEach {
        list.add(
            Books(
                it.bookImage,
                it.bookName,
                it.authorImage,
                it.authorName,
                it.isbn,
                it.bookTypeImage,
                it.bookType,
                it.favorite,
                it.description,
            )
        )
    }
    return list
}