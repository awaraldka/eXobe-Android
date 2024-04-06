package com.exobe.utils

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Looper
import android.util.Log
import android.widget.Toast
import android.location.Address
import android.location.Geocoder
import java.io.IOException
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.PendingResult
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.*


object LocationClass {

    data class LocationData(val latitude: Double, val longitude: Double)


    fun getCurrentLocation(context: Context) {
        val locationRequest = LocationRequest()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 3000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        LocationServices.getFusedLocationProviderClient(context)
            .requestLocationUpdates(locationRequest, object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    super.onLocationResult(locationResult)
                    LocationServices.getFusedLocationProviderClient(context)
                        .removeLocationUpdates(this)
                    if (locationResult != null && locationResult.locations.size > 0) {
                        val latestlocIndex = locationResult.locations.size - 1
                        val lati = locationResult.locations[latestlocIndex].latitude
                        val longi = locationResult.locations[latestlocIndex].longitude
                        SavedPrefManager.saveStringPreferences(context, SavedPrefManager.LAT,
                            lati.toString()
                        )
                        SavedPrefManager.saveStringPreferences(context, SavedPrefManager.LONG,
                            longi.toString()
                        )
                        SavedPrefManager.setLatitudeLocation(lati)
                        SavedPrefManager.setLongitudeLocation(longi)

                    } else {

                    }
                }
            }, Looper.getMainLooper())
    }

    fun displayLocationSettingsRequest(context: Context) {
        val googleApiClient = GoogleApiClient.Builder(context)
            .addApi(LocationServices.API).build()
        googleApiClient.connect()
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 10000
        locationRequest.fastestInterval = (10000 / 2).toLong()
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        builder.setAlwaysShow(true)
        val result: PendingResult<LocationSettingsResult> =
            LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build())
        result.setResultCallback(object : ResultCallback<LocationSettingsResult?>{
            override fun onResult(result: LocationSettingsResult) {
                val status: Status = result.status
                when (status.getStatusCode()) {
                    LocationSettingsStatusCodes.SUCCESS -> Log.i(
                        "LOCATION_TAG",
                        "All location settings are satisfied."
                    )
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        Log.i(
                            "LOCATION_TAG",
                            "Location settings are not satisfied. Show the user a dialog to upgrade location settings "
                        )
                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(
                                context as Activity,
                                1
                            )
                        } catch (e: SendIntentException) {
                            Log.i("LOCATION_TAG", "PendingIntent unable to execute request.")
                        }
                    }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> Log.i(
                        "LOCATION_TAG",
                        "Location settings are inadequate, and cannot be fixed here. Dialog not created."
                    )
                }
            }
        })
    }

    fun getLocation(context: Activity, fusedLocationClient: FusedLocationProviderClient, callback: (LocationData?) -> Unit) {
        try {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context, Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                callback(null)
                return
            }

            fusedLocationClient.lastLocation.addOnCompleteListener(context) { task ->
                if (task.isSuccessful && task.result != null) {
                    try {
                        val lastLocation = task.result
                        val latitude = lastLocation.latitude
                        val longitude = lastLocation.longitude

                        callback(LocationData(latitude, longitude))
                    } catch (e: Exception) {
                        e.printStackTrace()
                        callback(null)
                    }
                } else {
                    callback(null)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            callback(null)
        }
    }



    fun openMapToShowRoute(context: Context,startLat:Double,startLong:Double,latDestination:Double,longDestination:Double){
        val srcAdd = "&origin=$startLat,$startLong"
        val desAdd = "&destination=$latDestination,$longDestination"
        val link = "https://www.google.com/maps/dir/?api=1&travelmode=driving$srcAdd$desAdd"
        Log.e("Url Polyline?", link)
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            context.startActivity(intent)
        } catch (ane: ActivityNotFoundException) {
            Toast.makeText(context, "Please Install Google Maps ", Toast.LENGTH_LONG).show()
        } catch (ex: java.lang.Exception) {
            ex.message
        }
    }


    fun getAddressCoordinates(context: Context, address: String): Pair<Double, Double>? {
        val geocoder = Geocoder(context)
        try {
            val addresses: MutableList<Address>? = geocoder.getFromLocationName(address, 1)
            if (addresses!!.isNotEmpty()) {
                val latitude = addresses[0].latitude
                val longitude = addresses[0].longitude
                return Pair(latitude, longitude)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null // Return null if the address is invalid or an error occurs
    }





}