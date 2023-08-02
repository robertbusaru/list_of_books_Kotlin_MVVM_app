package com.example.listofbooks.api
import com.example.listofbooks.Books
import retrofit2.Response
import retrofit2.http.GET

interface ApiGetService {
    @GET("b/SDLA")
    suspend fun getPost(): Response<List<Books>>
}