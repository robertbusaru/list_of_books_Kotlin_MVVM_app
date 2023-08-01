package com.example.listofbooks

import Books
import com.example.listofbooks.model.ApiResult
import com.example.listofbooks.room.BookEntity
import com.example.listofbooks.room.LocalDataSource
import retrofit2.Response

object Repository {

    private lateinit var localDataSource: LocalDataSource
    private lateinit var networkDataSource: NetworkDataSource

    operator fun invoke(localDataSource: LocalDataSource, remoteDataSource: NetworkDataSource) {
        this.localDataSource = localDataSource
        this.networkDataSource = remoteDataSource
    }

    suspend fun getBooks(): Any {
       return if (localDataSource.getNumberOfBooks()==0){
           when (val result = networkDataSource.getBooks()){
               is ApiResult.Success<*> -> localDataSource.insertBooks(result.data as List<BookEntity>)
               else -> {
                   localDataSource.getBooks()
               }
           }
        } else localDataSource.getBooks()
    }


    suspend fun getNetworkBooks(): Response<List<Books>> {
        return networkDataSource.getBooks()
    }
}