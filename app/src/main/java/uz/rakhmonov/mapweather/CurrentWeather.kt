package uz.rakhmonov.mapweather

import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.rakhmonov.mapweather.databinding.ActivityCurrentWeatherBinding
import uz.rakhmonov.mapweather.models.Weather
import uz.rakhmonov.mapweather.models.WeatherData
import uz.rakhmonov.mapweather.retrofit.MyApiClient
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "CurrentWeather"

class CurrentWeather : AppCompatActivity() {
    private val binding by lazy { ActivityCurrentWeatherBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val latitude = intent.getDoubleExtra("latitude", 34.0522)
        val longitude = intent.getDoubleExtra("longitude", 118.2437)
        Toast.makeText(this, "${latitude}, ${longitude}", Toast.LENGTH_SHORT).show()

        val geocoder = Geocoder(this, Locale.getDefault())
        val address = geocoder.getFromLocation(latitude, longitude, 1)



        CoroutineScope(Dispatchers.IO).launch {
            val weatherDataDeferred: Deferred<WeatherData?> = async {
                getWeatherData(latitude, longitude)

            }
            Log.d(TAG, "onCreate: bu mening logiiiiiiimmmmm ${weatherDataDeferred}")
            Log.d(TAG, "logiiiiiiimmmmm ${getWeatherData(latitude, longitude)}")
            val weatherData = weatherDataDeferred.await()


            withContext(Dispatchers.Main) {
                Log.d(TAG, "onCreate: ggagagagagagagagaxzxzxzxzxzxzx ${weatherData?.name}")



                binding.apply {
                    tvTownName.text = weatherData?.name.toString()
                    tvTemperature.text = (weatherData?.main?.temp?.minus(273.15))?.toInt().toString()
                    tvTime.text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
                    tvWind.text = weatherData?.main?.humidity.toString()
                    imgStatus.setImageResource(getStatusImage(weatherData?.weather?.first()))

                    address?.let { tvCityName.text = it[0].countryName }


                    mainLayout.visibility  = View.VISIBLE
                    progressBar.visibility = View.GONE

                }
            }

        }

    }
}