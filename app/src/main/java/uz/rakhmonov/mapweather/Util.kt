package uz.rakhmonov.mapweather

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uz.rakhmonov.mapweather.models.Weather
import uz.rakhmonov.mapweather.models.WeatherData
import uz.rakhmonov.mapweather.retrofit.MyApiClient

private val apiService = MyApiClient.getApiService()

suspend fun getWeatherData(lat: Double, lon: Double): WeatherData? {
    return withContext(Dispatchers.IO) {
        val response = MyApiClient.getApiService().getInfoWeather(lat, lon)
        Log.d("TAGTAGTAG", "getWeatherData: ${response.body()}")
        response.body()
    }}

fun getStatusImage(weather: Weather?): Int {
    if (weather != null) {
        return when (weather.icon){
            "01d" -> R.drawable.sun // clear sky day
            "02d" -> R.drawable.clouds // few clouds day
            "03d", "03n", "04d", "04n" -> R.drawable.cloudy // scattered clouds, broken clouds
            "09d", "09n", "10d", "10n" -> R.drawable.rain // rain, shower rain
            "11d", "11n" -> R.drawable.cloudyday // thunderstorm
            "13d", "13n" -> R.drawable.ic_baseline_ac_unit_24 // snow
//            "50d", "50n" -> R.drawable.fog // mist
            else -> {R.drawable.sun}
        }

    }
    return R.drawable.sun
}