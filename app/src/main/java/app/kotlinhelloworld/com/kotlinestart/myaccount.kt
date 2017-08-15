package app.kotlinhelloworld.com.kotlinestart

import android.app.Service
import android.content.Intent
import android.support.v7.widget.*
import android.support.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_myaccount.*
import org.jetbrains.anko.toast
import org.json.*
import com.github.kittinunf.fuel.Fuel
import android.content.Context
import android.location.Criteria
import android.location.LocationManager
import android.location.LocationListener
import android.util.AttributeSet
import android.widget.LinearLayout
import org.json.*
import com.google.android.gms.location.LocationServices
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import android.location.Location
import android.util.Log
import android.Manifest
import android.Manifest.*
import android.app.Activity
import android.content.ContentValues
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import io.vrinda.kotlinpermissions.PermissionCallBack
import io.vrinda.kotlinpermissions.PermissionsActivity
import io.vrinda.kotlinpermissions.PermissionFragment
import java.io.File
import android.os.Environment
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v4.content.FileProvider
import android.widget.Toast
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import android.widget.ImageView
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
class myaccount : AppCompatActivity() {
    var locationManager: LocationManager? = null
    private fun get_location()
    {
        try
        {
            locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, INTERVAL, DISTANCE, locationListeners[0])
            val gps_location = locationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            val gps_longitude = gps_location?.getLongitude()
            val gps_latitude = gps_location?.getLatitude()
            val full_location: String = gps_longitude.toString()+", "+gps_latitude.toString()
            var service_url = service_url()
            var custom_url = service_url.service_url
            Fuel.post(custom_url, listOf("todo" to "usermeta", "user" to user_name, "key" to "location", "value" to full_location)).responseString { request, response, result ->
                result.fold({ d ->
                    val data: String = d.toString()
                    //toast("service data - $data")
                    try
                    {
                        var service = JSONObject(data)
                        val service_status: String = service.getString("status")
                        toast("service - $service_status")
                    }
                    finally
                    {
                        toast("location send successfully")
                    }

                }, { err ->
                    toast("err --- " + err)
                })
            }
            toast("GPS Location --- $full_location")
        } catch (e: SecurityException) {
            Log.e(TAG, "Fail to request location update", e)
            toast("Fail to request location update --- GPS")
        } catch (e: IllegalArgumentException) {
            Log.e(TAG, "GPS provider does not exist", e)
            toast("GPS provider does not exist")
        }
    }
    private  fun takePhoto() {
        val intent1 = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent1.resolveActivity(packageManager) != null) {
            startActivityForResult(intent1, REQUEST_TAKE_PHOTO)
        }
    }
    private fun selectImageInAlbum() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_SELECT_IMAGE_IN_ALBUM)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myaccount)
        if(user_id == "" || user_name == "")
        {
            val i = Intent(this@myaccount, MainActivity::class.java)
            startActivity(i)
        }
        else
        {
            //myaccountinfo.text = "user id: "+ user_id+" --- username: "+ user_name
            var service_url = service_url()
            var custom_url = service_url.service_url
            val user_activity_url = "$custom_url?todo=activity&user=$user_name"
            myaccountinfo.text = "User activity for: $user_name"
            //Camera section
            takepic_camera.setOnClickListener{
                takePhoto()
            }
            takepic_gallery.setOnClickListener {
                selectImageInAlbum()
            }
            Fuel.get(user_activity_url).responseString { request, response, result ->
                result.fold({ d ->
                    val data: String = d.toString()
                    var service = JSONObject(data)
                    if(service.getString("status") == "success")
                    {
                        var service_data = service.getJSONArray("data")
                        //var json_service_data = JSONArray(service_data)
                        var output_var = ""
                        var current_data: String = ""
                        for (item in 0..(service_data.length() - 1))
                        {
                            //current_data = service_data.getJSONObject(item).get("content").toString()
                            current_data = service_data.getJSONObject(item).getString("content")+"---"+service_data.getJSONObject(item).getString("datetime")+"\r\n"
                            output_var = "$output_var  $current_data"
                            //toast(current_data)
                        }
                        activity_info.text = output_var
                        toast("reading location plz wait")
                        if(locationManager == null)
                            locationManager = applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                        {
                            get_location()
                        }
                        else
                        {
                            toast("location permission is not granted")
                            val i = Intent(this@myaccount, MainActivity::class.java)
                            i.putExtra("user",user_id);
                            startActivity(i)
                        }
                    }
                    else
                    {
                        activity_info.text = service.getString("data")
                    }

                }, { err ->
                    toast("err --- " + err)
                })
            }
        }
    }
    companion object {
        val TAG = "LocationTrackingService"
        private val REQUEST_TAKE_PHOTO = 0
        private val REQUEST_SELECT_IMAGE_IN_ALBUM = 1
        val INTERVAL = 1000.toLong() // In milliseconds
        val DISTANCE = 10.toFloat() // In meters

        val locationListeners = arrayOf(
                LTRLocationListener(LocationManager.GPS_PROVIDER),
                LTRLocationListener(LocationManager.NETWORK_PROVIDER)
        )

        class LTRLocationListener(provider: String) : android.location.LocationListener {

            val lastLocation = Location(provider)

            override fun onLocationChanged(location: Location?) {
                lastLocation.set(location)
                // TODO: Do something here
            }

            override fun onProviderDisabled(provider: String?) {
            }

            override fun onProviderEnabled(provider: String?) {
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            }

        }
    }
}