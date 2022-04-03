package com.georgian.farmington

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.georgian.farmington.databinding.ActivityRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class RegistrationActivity : AppCompatActivity() {

    // for binding data to firebase
    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var userfirebase: FirebaseAuth

//    var reg = findViewById<Button>(R.id.btnregister)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        var fName = findViewById<EditText>(R.id.etxtfname)
        var lName = findViewById<EditText>(R.id.etxtlname)
        var pwd = findViewById<EditText>(R.id.etxtpassword)
        var cPwd = findViewById<EditText>(R.id.etxtcpassword)
        var mobile = findViewById<EditText>(R.id.etxtmobile)
        var email = findViewById<EditText>(R.id.etxtemail)
        var btncancel = findViewById<Button>(R.id.btncancelregister)

        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userfirebase = FirebaseAuth.getInstance()

        // if user clicks on cancel button
//       binding.btncancelregister.setOnClickListener ({
//            fName.text.clear()
//            lName.text.clear()
//            mobile.text.clear()
//            email.text.clear()
//            pwd.text.clear()
//            cPwd.text.clear()
//        })

        binding.btnregister.setOnClickListener({
//            registerUser()
          fName.text.clear()
            lName.text.clear()
          mobile.text.clear()
          email.text.clear()
           pwd.text.clear()
          cPwd.text.clear()
        })
    }

    private fun registerUser() {
        val email = binding.etxtemail.text.toString().trim()
        val pwd = binding.etxtpassword.text.toString().trim()

        if (email.isNotEmpty() && pwd.isNotEmpty()) {
            userfirebase.createUserWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(RegistrationActivity()) { task ->

                    if (task.isSuccessful()) {

                        Toast.makeText(this, "user registered in successfully", Toast.LENGTH_SHORT)
                            .show()
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, task.exception!!.message, Toast.LENGTH_SHORT)
                            .show()
                    }
//
                }
                .addOnFailureListener(RegistrationActivity()) { task ->
                    Toast.makeText(this, "failure", Toast.LENGTH_SHORT)
                        .show()
                }
        } else {
            Toast.makeText(this, "Enter username and password", Toast.LENGTH_SHORT).show()
        }
    }
  //   if user presses back button
    override fun onBackPressed() {
        var intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()

        super.onBackPressed()
    }
}

