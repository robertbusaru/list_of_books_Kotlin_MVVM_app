package com.example.listofbooks

import com.example.listofbooks.room.LocalDataSource

object Repository {

    private lateinit var localDataSource: LocalDataSource
    private lateinit var networkDataSource: NetworkDataSource

    operator fun invoke(
        localDataSource: LocalDataSource, remoteDataSource: NetworkDataSource
    ): Repository {
        this.localDataSource = localDataSource
        this.networkDataSource = remoteDataSource
        return this
    }

    suspend fun getBooks(): List<Books> {

        if (localDataSource.getNumberOfBooks() == 0) {
            val result = networkDataSource.getBooks()
            if (result.isSuccessful && result.body() != null) {
                val mappedList = result.body()!!.mapBooksToBookEntity()
                localDataSource.insertBooks(mappedList)
                return result.body()!!
            }
        } else {
            return localDataSource.getBooks().mapBookEntityToBooks()


        }
        return arrayListOf()
    }

}
