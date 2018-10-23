package org.wit.hillfort.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.wit.hillfort.R
import org.wit.hillfort.models.Location

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

  private lateinit var map: GoogleMap
  var location = Location()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_maps)
    location = intent.extras.getParcelable<Location>("location")
    val mapFragment = supportFragmentManager
        .findFragmentById(R.id.map) as SupportMapFragment
    mapFragment.getMapAsync(this)
  }

//This is where locations can be added
  override fun onMapReady(googleMap: GoogleMap) {
    map = googleMap
    val loc = LatLng(location.lat,location.lng)
    val options = MarkerOptions()
        .title("Hillfort")
        .snippet("GPS: "+ loc.toString())
        .draggable(true)
        .position(loc)
    map.addMarker(options)
    map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc,location.zoom))
  }
}
