package com.example.listofbooks.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.listofbooks.Books
import com.example.listofbooks.NetworkDataSourceImpl
import com.example.listofbooks.Repository
import com.example.listofbooks.room.BookRoomDatabase
import com.example.listofbooks.room.LocalDataSourceImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BooksViewModel(application: Application) : AndroidViewModel(application) {

    val booksListLiveData: MutableLiveData<List<Books>> = MutableLiveData()
    private val networkRepository = NetworkDataSourceImpl(RestApiClient.apiClient)
    private val localRepository =
        LocalDataSourceImpl(BookRoomDatabase.getDatabase(application).getBookDao())
    val isLoadingScreen: MutableLiveData<Boolean> = MutableLiveData()
    private val finalRepository = Repository.invoke(localRepository, networkRepository)

    fun getPost() {
        isLoadingScreen.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                booksListLiveData.postValue(finalRepository.getBooks())
                isLoadingScreen.postValue(false)
            } catch (e: Exception) {
                isLoadingScreen.value = false
                booksListLiveData.value = null
            }
        }
    }


}
