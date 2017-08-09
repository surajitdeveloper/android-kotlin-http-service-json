package app.kotlinhelloworld.com.kotlinestart

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.*
import kotlinx.android.synthetic.main.activity_main2.*
import android.view.*
import android.support.*
import org.jetbrains.anko.toast
class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        btn_back.setOnClickListener {
            //val user = intent.extras.getString("user")
            //toast("Main2 Activity --- Hi I am "+user+" Key User public var - "+key_user+ " key from class - "+global_var.key_user)
            toast("Main2 Activity ---  Key User public var - "+user_id)
            val i = Intent(this@Main2Activity, MainActivity::class.java)
            //i.putExtra("user",user);
            startActivity(i)
            finish()
        }
    }
}
