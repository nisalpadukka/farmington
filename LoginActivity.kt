package com.example.farmington

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.farmington.CommonUtils.myurl
import com.google.mlkit.common.sdkinternal.CommonUtils
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    lateinit var useremail:String
    lateinit var userpassword:String
    var done:String="No"
//    var myurl1= CommonUtils.http://127.0.0.1/LoginRegistration;
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

                var asynchttpclient = AsyncHttpClient()
                var params = RequestParams()
                params.put("email", uname.text)
                params.put("password", upass.text)

                asynchttpclient.get(
                    myurl+"login.php?", params,
                    object : JsonHttpResponseHandler() {
                        override fun onSuccess(
                            statusCode: Int,
                            headers: Array<out Header>?,
                            response: JSONObject?
                        ) {

                            try {
                                var ja: JSONArray
                                ja = response?.getJSONArray("data") ?: JSONArray()
                                for (i in 0..ja.length()) {
                                    var jb: JSONObject = ja.getJSONObject(i)
                                    var em = jb.getString("email")
                                    var pass = jb.getString("password")
                                    var fname = jb.getString("name")
                                    var lname = jb.getString("address")
                                    var mob = jb.getString("imeino")
                                    if ((em.equals(uname.text.toString())) and (pass.equals(upass.text.toString()))) {
                                        done = "Yes"
                                        var pref =
                                            getSharedPreferences("pref", MODE_PRIVATE)
                                        var edit = pref.edit()
                                        edit.putString("name",uname.text.toString())
                                        edit.putString("userfname", fname)
                                        edit.putString("userlname", lname)
                                        edit.putString("useremail", em)
                                        edit.putString("usermobile", mob)
                                        edit.apply()
                                        var intent = Intent(this@LoginActivity, MainActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                        break
                                    } else {
                                        done = "No"
                                    }
                                }

                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                            super.onSuccess(statusCode, headers, response)
                            if (done.equals("No")) {
                                Toast.makeText(
                                    this@LoginActivity,
                                    "You are not a valid user",
                                    Toast.LENGTH_LONG
                                ).show()
                                uname.text.clear()
                                upass.text.clear()
                            }
                            /*Toast.makeText(this@LoginActivity, "dvd" + response, Toast.LENGTH_LONG)
                                .show()*/
                        }

                        override fun onFailure(
                            statusCode: Int,
                            headers: Array<out Header>?,
                            throwable: Throwable?,
                            errorResponse: JSONArray?
                        ) {
                            super.onFailure(statusCode, headers, throwable, errorResponse)
                            Toast.makeText(
                                this@LoginActivity,
                                "dvd" + errorResponse,
                                Toast.LENGTH_LONG
                            ).show()

                        }
                    })
            }
        }
        reg.setOnClickListener {
            var intent=Intent(this,RegistrationActivity::class.java)
            startActivity(intent)
            finish()
        }
        canbtn.setOnClickListener {
            uname.text.clear()
            upass.text.clear()
        }

    }
}
