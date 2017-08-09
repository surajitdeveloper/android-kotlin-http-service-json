package app.kotlinhelloworld.com.kotlinestart

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_myaccount.*
import org.jetbrains.anko.toast
import org.json.*
import com.github.kittinunf.fuel.Fuel
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
            myaccountinfo.text = "user id: "+ user_id+" --- username: "+ user_name
        }
    }
}
