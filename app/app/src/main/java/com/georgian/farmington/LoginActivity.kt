package com.georgian.farmington

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView


class LoginActivity : AppCompatActivity() {

    lateinit var useremail:String
    lateinit var userpassword:String
    var done:String="No"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var reg=findViewById<TextView>(R.id.ureg)
        var uname=findViewById<EditText>(R.id.etun)
        var upass=findViewById<EditText>(R.id.etpass)
        var lgnbtn=findViewById<Button>(R.id.btloginu)
        var canbtn=findViewById<Button>(R.id.btncancelu)
        //var i:Int
        lgnbtn.setOnClickListener {
            if(TextUtils.isEmpty(uname.text)){
                uname.setError("Username required")
            }
            else if(TextUtils.isEmpty(upass.text)){
                upass.setError("Password required")
            }
            else {

            }
        }
        reg.setOnClickListener {

        }
        canbtn.setOnClickListener {
            uname.text.clear()
            upass.text.clear()
        }

    }
}
