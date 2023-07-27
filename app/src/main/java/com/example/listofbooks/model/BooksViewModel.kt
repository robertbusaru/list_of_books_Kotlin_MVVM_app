package com.example.listofbooks.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listofbooks.Books
import kotlinx.coroutines.launch

class BooksViewModel : ViewModel() {

    val booksListLiveData: MutableLiveData<List<Books>> = MutableLiveData()
    private val repository = NetworkRepository.invoke(RestApiClient.apiClient)
    val isLoadingScreen: MutableLiveData<Boolean> = MutableLiveData()

    fun getPost() {
        isLoadingScreen.value = true
        viewModelScope.launch {
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
        }
    }

}
