package uz.rakhmonov.mapweather.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import uz.rakhmonov.mapweather.models.WeatherData

const val APIKEY="8a21a95db9bff15ebd4164caa4fcb84f"

interface MyApiService {
    @GET("weather")
    suspend fun getInfoWeather (
        @Query("lat")lat:Double,
        @Query("lon")long:Double,
        @Query("appid")apiKey:String=APIKEY
    ):Response<WeatherData>
}