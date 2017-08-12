package app.kotlinhelloworld.com.kotlinestart

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
import android.util.AttributeSet
import android.widget.LinearLayout
import org.json.*

/*
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationRequest;
 */
class myaccount : AppCompatActivity() {

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
                        /*
                        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
double longitude = location.getLongitude();
double latitude = location.getLatitude();
private final LocationListener locationListener = new LocationListener() {
    public void onLocationChanged(Location location) {
        longitude = location.getLongitude();
        latitude = location.getLatitude();
    }
}

lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
                         */
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
}
