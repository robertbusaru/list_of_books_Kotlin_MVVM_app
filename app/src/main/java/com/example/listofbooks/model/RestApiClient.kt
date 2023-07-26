import com.example.listofbooks.api.ApiGetService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RestApiClient {

    val apiClient: ApiGetService by lazy {
        val retrofit: Retrofit = Retrofit.Builder().baseUrl("https://www.jsonkeeper.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        return@lazy retrofit.create()
    }


}