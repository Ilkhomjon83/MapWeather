package uz.rakhmonov.mapweather.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MyApiClient {
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    private fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getApiService(): MyApiService {
        return getRetrofit().create(MyApiService::class.java)
    }
}