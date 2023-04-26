package uz.rakhmonov.mapweather

import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import uz.rakhmonov.mapweather.databinding.ActivityMapsBinding

//const val APIKEY="8a21a95db9bff15ebd4164caa4fcb84f"


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    lateinit var locationoManager:LocationManager
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            // Use the location
            val latitude = location.latitude
            val longitude = location.longitude

            // Add a marker to the map

//            map.addMarker(MarkerOptions().position(LatLng(latitude,longitude)))!!
//            map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude), 15f))
            val user = LatLng(latitude, longitude)
            mMap.animateCamera(CameraUpdateFactory.newLatLng(user))
            mMap.addMarker(MarkerOptions().position(user))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(user,0f))


        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        locationoManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
        val myTown = LatLng(40.37320693889889, 71.79560786908752 )
        googleMap.addMarker(MarkerOptions().position(myTown).title("Marker in Ferghana"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(myTown))


        mMap.setOnMapClickListener { latLng ->

            val intent = Intent(this, CurrentWeather::class.java).apply {
                putExtra("latitude",latLng.latitude)
                putExtra("longitude",latLng.longitude)
            }
            startActivity(intent)

        }
    }
}