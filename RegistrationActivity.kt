package com.example.farmington

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class RegistrationActivity : AppCompatActivity() {

    lateinit var ftname:String
    lateinit var ltname:String
    lateinit var password:String
   lateinit var cpassword:String
    lateinit var email:String
    lateinit var mobile:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        //
        var register = findViewById<Button>(R.id.btnregister)
        var canreg = findViewById<Button>(R.id.btncancelregister)
        var fn = findViewById<EditText>(R.id.fname)
        var ln = findViewById<EditText>(R.id.lname)
        var mob = findViewById<EditText>(R.id.mobile)
        var em = findViewById<EditText>(R.id.email)
        var pass = findViewById<EditText>(R.id.password)
        var cpass = findViewById<EditText>(R.id.cpassword)

        // validating empty fields
        register.setOnClickListener {

            if (TextUtils.isEmpty(fn.text)) {
                fn.setError("FirstName required")
            } else if (TextUtils.isEmpty(ln.text)) {
                ln.setError("Last Name required")
            } else if (TextUtils.isEmpty(mob.text)) {
                mob.setError("Mobile number required")
            } else if (TextUtils.isEmpty(pass.text)) {
                pass.setError("Password required")
            } else if (TextUtils.isEmpty(cpass.text)) {
                cpass.setError("Confirm password required")
            }else if (TextUtils.isEmpty(em.text)) {
                cpass.setError("Email required")
            } else if (pass.text.toString() != cpass.text.toString()) {
                pass.text.clear()
                cpass.text.clear()
                Toast.makeText(
                    this,
                    "password and confirm password does not match",
                    Toast.LENGTH_LONG
                ).show()
            } else {

                ftname=fn.text.toString()
                ltname=ln.text.toString()
                email=em.text.toString()
                mobile=mob.text.toString()
                password=pass.text.toString()
               cpassword=cpass.text.toString()


                var asynchttpclient= AsyncHttpClient()
                var params= RequestParams()

                params.put("FName",ftname)
                params.put("LName",ltname)
                params.put("Email",email)
                params.put("Pwd",password)
                params.put("Phone",mobile)
                //                params.put("cpsw",cpassword)
                // params.put("ustype","user_amitha")
                // params.put("androidid","")
                // params.put("gender","")

//                Toast.makeText(this@RegistrationActivity,"http://127.0.0.1/loginregistration/registration.php? "+params,Toast.LENGTH_LONG).show()
    try {


                asynchttpclient.get("http://127.0.0.1/loginregistration/registration.php?",params, object : JsonHttpResponseHandler(){

                    override fun onSuccess(
                        statusCode: Int,
                        headers: Array<out Header>?,
                        response: JSONObject?
                    ) {
                        super.onSuccess(statusCode, headers, response)
                       Toast.makeText(this@RegistrationActivity,"Success"+response,Toast.LENGTH_LONG).show()
                    }

                    override fun onFailure(
                        statusCode: Int,
                        headers: Array<out Header>?,
                        throwable: Throwable?,
                        errorResponse: JSONArray?
                    ) {
                        super.onFailure(statusCode, headers, throwable, errorResponse)
                        Toast.makeText(this@RegistrationActivity,"error"+errorResponse, Toast.LENGTH_LONG).show()

                    }
                })
    }catch (e :Exception){
        Toast.makeText(this@RegistrationActivity,"Exception "+e,Toast.LENGTH_LONG).show()
    }
                //Toast.makeText(this, "User registered successfully", Toast.LENGTH_LONG).show()
//              var intent = Intent(this, LoginActivity::class.java)
//                startActivity(intent)
//                finish()
            }
        }
        canreg.setOnClickListener {
            fn.text.clear()
            ln.text.clear()
            mob.text.clear()
            em.text.clear()
            pass.text.clear()
            cpass.text.clear()
        }

    }

    override fun onBackPressed() {
        var intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()

        super.onBackPressed()
    }
}

