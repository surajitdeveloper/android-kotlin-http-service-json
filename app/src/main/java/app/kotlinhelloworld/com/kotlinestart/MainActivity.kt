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
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.util.toHexString
import com.github.kittinunf.fuel.*
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_login.setOnClickListener{
            result_area.text = "clicl login"
            val username: String = user.text.toString()
            val passwd: String = passwd.text.toString()
            val userlength = username.length
            val passlength = passwd.length
            if(userlength == 0 || passlength == 0)
            {
                toast("Please Enter Username and Password")
            }
            else
            {
                val custom_url = "http://181.215.99.99/econstra/advicegate/service.php?todo=login&user="+username+"&password="+passwd
                //toast("try Your entered username: "+username+" and password is: "+passwd+ "userlength: "+userlength+" Passlength: "+passlength)
                //toast(custom_url)
                toast("Please Wait")
                Fuel.get(custom_url).responseString { request, response, result ->
                    //do something with response
                    result.fold({ d ->
                        val data: String = d.toString()
                        result_area.text = "Response - "+d

                    }, { err ->
                        toast("err --- "+err)
                    })
                }
            }

        }
        btn_reg.setOnClickListener {
            result_area.text = "Click Reg"
            val user = "Surajit"
            toast("Main Activity --- Hi I am "+user)
            val i = Intent(this@MainActivity, Main2Activity::class.java)
            i.putExtra("user",user);
            startActivity(i)
            finish()
        }

    }
}
