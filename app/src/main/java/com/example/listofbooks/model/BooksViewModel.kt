package com.example.listofbooks.model

import Books
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.listofbooks.Repository
import com.example.listofbooks.room.BookRoomDatabase
import com.example.listofbooks.room.LocalRepository
import kotlinx.coroutines.launch

class BooksViewModel(application: Application) : AndroidViewModel(application) {

    val booksListLiveData: MutableLiveData<List<Books>> = MutableLiveData()
    private val repository = NetworkRepository.invoke(RestApiClient.apiClient)
    val isLoadingScreen: MutableLiveData<Boolean> = MutableLiveData()

    fun getPost() {
        isLoadingScreen.value = true
        viewModelScope.launch {
            try {
//                val localRepository = getLocalRepository(getApplication())
                when (val response = repository.getBooksData()) {
                    is ApiResult.Success -> {
                        isLoadingScreen.value = false
                        booksListLiveData.value = response.data


                    }

                    is ApiResult.Unauthorized -> {
                        booksListLiveData.value = null
                        isLoadingScreen.value = false
                    }

                    is ApiResult.Exception -> {
                        booksListLiveData.value = null
                        isLoadingScreen.value = false
                    }

                    is ApiResult.Error -> {
                        booksListLiveData.value = null
                        isLoadingScreen.value = false
                    }

                    is ApiResult.OtherException -> {
                        booksListLiveData.value = null
                        isLoadingScreen.value = false
                    }
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
