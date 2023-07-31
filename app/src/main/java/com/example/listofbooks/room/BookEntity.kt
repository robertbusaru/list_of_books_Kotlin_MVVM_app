package com.example.listofbooks.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books_table")
data class BookEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val bookImage: String,
    val bookName: String,
    val authorImage: String,
    val authorName: String,
    val isbn: String,
    val bookTypeImage: String,
    val bookType: String,
    var favorite: Boolean,
    val description: String
)