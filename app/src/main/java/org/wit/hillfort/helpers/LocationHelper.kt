package org.wit.hillfort.helpers
//This calass is for checking the location of the user
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import android.util.Log
import com.google.android.gms.location.LocationRequest

val REQUEST_PERMISSIONS_REQUEST_CODE = 34

fun checkLocationPermissions(activity: Activity) : Boolean {
  if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
    return true
  }
  else {
    ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSIONS_REQUEST_CODE)
    return false
  }
}

fun isPermissionGranted(code: Int, grantResults: IntArray): Boolean {
  var permissionGranted = false;
  if (code == REQUEST_PERMISSIONS_REQUEST_CODE) {
    when {
      grantResults.isEmpty() -> Log.i("Location", "User stopped interaction.")
      (grantResults[0] == PackageManager.PERMISSION_GRANTED) -> {
        permissionGranted = true
        Log.i("Location", "Permission Granted.")
      }
      else -> Log.i("Location", "Permission Denied.")
    }
  }
  return permissionGranted
}

@SuppressLint("Restricted Api")
fun createDefaultLocationRequest() : LocationRequest {
  val locationRequest = LocationRequest().apply {
    interval = 1000
    fastestInterval = 500
    priority = LocationRequest.PRIORITY_HIGH_ACCURACY
  }
  return locationRequest
}