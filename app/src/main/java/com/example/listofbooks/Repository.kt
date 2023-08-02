package com.example.listofbooks

import Books
import android.util.Log
import com.example.listofbooks.model.ApiResult
import com.example.listofbooks.room.BookEntity
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
        val result = networkDataSource.getBooks()
        Log.d("getnumberofbooks", "${localDataSource.getNumberOfBooks()}")
        if (localDataSource.getNumberOfBooks() != 0) {
            if (result.isSuccessful && result.body() != null) {
                val mappedList = result.body()!!.mapBooksToBookEntity()
                localDataSource.insertBooks(mappedList)
                return result.body()!!
            }
        } else {
            Log.d("Network data source", "${result.toString()}")
            return localDataSource.getBooks().mapBookEntityToBooks()


        }
        return arrayListOf()
    }


//    suspend fun getNetworkBooks(): ApiResult<List<Books>> {
//        return networkDataSource.getBooks()
//    }
}
