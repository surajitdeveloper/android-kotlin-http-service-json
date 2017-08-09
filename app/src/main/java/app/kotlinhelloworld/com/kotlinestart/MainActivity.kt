package app.kotlinhelloworld.com.kotlinestart
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AlertDialog
import android.os.*
import kotlinx.android.synthetic.main.activity_main.*
import android.view.*
import android.support.*
import org.jetbrains.anko.toast
import java.net.HttpURLConnection
import java.net.URL
import android.net.*
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import android.widget.*
import java.lang.StringBuilder
import org.json.*
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.util.toHexString
import com.github.kittinunf.fuel.*

public var user_id = ""
public var user_name = ""

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(user_id == "")
        {
            result_area.text = "Not logged in"
        }
        else
        {
            result_area.text = "logged in as "+user_name
        }
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
                val custom_url = "http://181.215.99.99/econstra/advicegate/service.php?todo=login&username="+username+"&password="+passwd
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
