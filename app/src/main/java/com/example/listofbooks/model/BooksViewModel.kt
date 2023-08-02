package com.example.listofbooks.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.listofbooks.NetworkDataSourceImpl
import com.example.listofbooks.Repository
import com.example.listofbooks.room.BookEntity
import com.example.listofbooks.room.BookRoomDatabase
import com.example.listofbooks.room.LocalDataSourceImpl
import kotlinx.coroutines.launch

class BooksViewModel(application: Application) : AndroidViewModel(application) {

    val booksListLiveData: MutableLiveData<List<BookEntity>> = MutableLiveData()
    private val repository = NetworkRepository.invoke(RestApiClient.apiClient)
    private val networkRepository = NetworkDataSourceImpl(RestApiClient.apiClient)
    private val localRepository = LocalDataSourceImpl(BookRoomDatabase.getDatabase(application).getBookDao())
    val isLoadingScreen: MutableLiveData<Boolean> = MutableLiveData()
    private val finalRepository = Repository.invoke(localRepository, networkRepository)

    fun getPost() {
        isLoadingScreen.value = true
        viewModelScope.launch {
            try {
                Log.d("localr", "${localRepository.getBooks()}")
//                val localRepository = getLocalRepository(getApplication())
                when (val response = finalRepository.getBooks()) {
                    is ApiResult.Success<*> -> {
                        Log.d("localr", "${localRepository.getBooks().size}")
                        isLoadingScreen.value = false
                        response.data as List<BookEntity>
                        booksListLiveData.value = response.data
                    }

//                    is ApiResult.Unauthorized -> {
//                        booksListLiveData.value = null
//                        isLoadingScreen.value = false
//                    }
//
//                    is ApiResult.Exception -> {
//                        booksListLiveData.value = null
//                        isLoadingScreen.value = false
//                    }
//
//                    is ApiResult.Error -> {
//                        booksListLiveData.value = null
//                        isLoadingScreen.value = false
//                    }
//
//                    is ApiResult.OtherException -> {
//                        booksListLiveData.value = null
//                        isLoadingScreen.value = false
//                    }
                }
            } catch (e: Exception) {
                isLoadingScreen.value = false
                booksListLiveData.value = null
            }
        }
    }

//    private suspend fun insertBooksToDatabase(
//        books: List<Books>,
//        localRepository: LocalRepository
//    ) {
//        localRepository.insertBooks(books)
//    }

//    private fun getLocalRepository(application: Application): Any {
//        return Repository.getBooks()
//        return LocalRepository(BookRoomDatabase.getDatabase(application).getBookDao())
//    }

}
