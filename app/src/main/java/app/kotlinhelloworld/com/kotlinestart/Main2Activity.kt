package app.kotlinhelloworld.com.kotlinestart
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.*
import com.github.kittinunf.fuel.Fuel
import kotlinx.android.synthetic.main.activity_main2.*
import org.json.JSONObject


class Main2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        btn_reg.setOnClickListener {
            val username: String = user.text.toString()
            val password: String = passwd.text.toString()
            val email_id: String = email.text.toString()
            if(username.length != 0 && password.length != 0 && email_id.length != 0)
            {
                var service_url = service_url()
                var service_link = service_url.service_url
                val register_url = "$service_link?todo=register&username=$username&email=$email_id&password=$password"
                toast("please wait")
                Fuel.get(register_url).responseString { request, response, result ->
                    result.fold({ d ->
                        val data: String = d.toString()
                        var service = JSONObject(data)
                        val service_status: String = service.getString("status")
                        if(service_status == "success")
                        {
                            val service_data: String = service.getString("data")
                            val user_data = JSONObject(service_data)
                            user_id = user_data.getString("user_id")
                            user_name = user_data.getString("username")
                            val i = Intent(this@Main2Activity, myaccount::class.java)
                            i.putExtra("user",user_id);
                            startActivity(i)
                        }
                        else
                        {
                            toast(service_status)
                            result_area.text = service.getString("data")
                        }
                    }, { err ->
                        toast("err --- " + err)
                    })
                }
            }
            else
            {
                toast("All field required")
                result_area.text = "All field required"
            }
        }
        btn_back.setOnClickListener {
            //val user = result_area.text = "All field required"intent.extras.getString("user")
            //toast("Main2 Activity --- Hi I am "+user+" Key User public var - "+key_user+ " key from class - "+global_var.key_user)
            toast("Main2 Activity ---  Key User public var - "+user_id)
            val i = Intent(this@Main2Activity, MainActivity::class.java)
            //i.putExtra("user",user);
            startActivity(i)
            finish()
        }
    }
}
