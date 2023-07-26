package com.example.listofbooks.model

import com.example.listofbooks.Books
import com.example.listofbooks.api.ApiGetService
import retrofit2.HttpException
import retrofit2.Response

object NetworkRepository : BooksDataSource {

    private lateinit var service: ApiGetService
    private const val TAG = "NetworkRepository"

    operator fun invoke(service: ApiGetService): BooksDataSource {
        NetworkRepository.service = service
        return this
    }

    override suspend fun getBooksData(): ApiResult<List<Books>> {
        return handleApi { service.getPost() }
    }

    private suspend fun <T : Any> handleApi(
        execute: suspend () -> Response<T>
    ): ApiResult<T> {
        return try {
            val response = execute()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                ApiResult.Success(body)
            } else {
                if (response.code() == 401) {
                    ApiResult.Unauthorized()
                } else {
                    val message: String? = null
                    ApiResult.Error(
                        code = response.code(), message = response.message(), parsedError = message
                    )
                }
            }
        } catch (e: HttpException) {
            ApiResult.Error(code = e.code(), message = e.message(), parsedError = null)
        } catch (e: Throwable) {
            ApiResult.Exception(message = e.message)
        }
    }
}