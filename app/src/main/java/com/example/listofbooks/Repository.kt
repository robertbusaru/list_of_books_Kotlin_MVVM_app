package com.example.listofbooks

import android.util.Log
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

        Log.d("Network_data_source", "a intrat")
        if (localDataSource.getNumberOfBooks() == 0) {
            val result = networkDataSource.getBooks()
            if (result.isSuccessful && result.body() != null) {
                Log.d("Network_data_source", networkDataSource.getBooks().body().toString())
                val mappedList = result.body()!!.mapBooksToBookEntity()
                Log.d("Network_data_source","mapped list count: " + mappedList.size.toString())
                localDataSource.insertBooks(mappedList)
                return result.body()!!
            }
        } else {
            Log.d("Network_data_source", "ceva`")
            return localDataSource.getBooks().mapBookEntityToBooks()


        }
        return arrayListOf()
    }

}
