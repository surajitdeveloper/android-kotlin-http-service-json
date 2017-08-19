package app.kotlinhelloworld.com.kotlinestart
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.*
import kotlinx.android.synthetic.main.activity_main.*
import org.json.*
import com.github.kittinunf.fuel.Fuel
import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.widget.Toast


var user_id = ""
var user_name = ""
val REQUEST_PERMISSION_LOCATION = 100
public fun Context.toast(message: CharSequence) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
class service_url()
{
    public var service_url = "http://154.16.249.162/econstra/advicegate/service.php"
}


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA), REQUEST_PERMISSION_LOCATION)

        var handler: sqlhandler
        handler = sqlhandler(this@MainActivity)
        /*
        database.use {

            var c = rawQuery("select COUNT(*) as count from user", null)
            while (c.moveToNext())
            {
                var count = c.getString(c.getColumnIndex("count"))
                val get_count: Int = count.toInt()
                if (get_count > 0)
                {
                    var get_query = rawQuery("select * from user", null)
                    while (get_query.moveToNext())
                    {
                        user_id = get_query.getString(get_query.getColumnIndex("id"))
                        user_name = get_query.getString(get_query.getColumnIndex("username"))
                        val i = Intent(this@MainActivity, myaccount::class.java)
                        i.putExtra("user",user_id);
                        startActivity(i)
                    }

                }
            }
        }

         */

        btn_login.setOnClickListener{
            //result_area.text = "clicl login"
            val username: String = user.text.toString()
            val passwd: String = password.text.toString()
            val userlength = username.length
            val passlength = passwd.length
            if(userlength == 0 || passlength == 0)
            {
                toast("Please Enter Username and Password")
            }
            else
            {
                var service_url = service_url()
                var custom_url = service_url.service_url+"?todo=login&username=$username&password=$passwd"
                toast("Please Wait")
                Fuel.get(custom_url).responseString { request, response, result ->
                    result.fold({ d ->
                        val data: String = d.toString()
                        val json_obj = JSONObject(data)
                        val service_status = json_obj.get("status")
                        if(service_status == "success")
                        {
                            user.setText("")
                            password.setText("")
                            val service_data = json_obj.get("data").toString()
                            val json_data_parse = JSONObject(service_data)
                            user_id = json_data_parse.get("id").toString()
                            user_name = json_data_parse.get("username").toString()
                            val user_email = json_data_parse.getString("email")

                            /*
                            database.use {
                                val values: ContentValues
                                values = ContentValues()
                                values.put("id", user_id)
                                values.put("username", user_name)
                                values.put("email",user_email)
                                insert("user",null,values)
                            }
                             */
                            //toast("key Global --- "+ key_user)
                            val i = Intent(this@MainActivity, myaccount::class.java)
                            i.putExtra("user",user_id);
                            startActivity(i)
                        }
                        else
                        {
                            val output = json_obj.get("data").toString()
                            result_area.text = output
                            toast(output)
                        }

                    }, { err ->
                        toast("err --- "+err)
                    })
                }
            }

        }
        btn_reg.setOnClickListener {
            //result_area.text = "Click Reg"
            //val user = "Surajit"

            //toast("Main Activity --- Hi I am "+user)
            user_id = "Sadhukhan"
            //toast("key Global --- "+ key_user)
            val i = Intent(this@MainActivity, Main2Activity::class.java)
            //i.putExtra("user",user);
            startActivity(i)
            finish()
        }

    }
}
