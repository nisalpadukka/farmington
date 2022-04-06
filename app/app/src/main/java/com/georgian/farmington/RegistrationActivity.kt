package com.georgian.farmington

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.georgian.farmington.databinding.ActivityRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase


class RegistrationActivity : AppCompatActivity() {

    // for binding data to firebase
    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var userfirebase: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        this.supportActionBar?.title = "Farmington - Registration"
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userfirebase = FirebaseAuth.getInstance()

        // if user clicks on cancel button
        binding.btncancelregister.setOnClickListener ({
            binding.etxtfname.text.clear()
            binding.etxtlname.text.clear()
            binding.etxtcpassword.text.clear()
            binding.etxtpassword.text.clear()
            binding.etxtemail.text.clear()
            binding.etxtmobile.text.clear()
        })

        binding.btnregister.setOnClickListener({
            registerUser()
        })
    }

    private fun registerUser() {
        val email = binding.etxtemail.text.toString().trim()
        val pwd = binding.etxtpassword.text.toString().trim()
        val fname = binding.etxtfname.text.toString().trim()
        val lname = binding.etxtlname.text.toString().trim()
        val mob = binding.etxtmobile.text.toString().trim()

        if (email.isNotEmpty() && pwd.isNotEmpty()) {
            userfirebase.createUserWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(RegistrationActivity()) { task ->

                    if (task.isSuccessful()) {

                        val profileUpdates = userProfileChangeRequest {
                            displayName = "$fname $lname"
                        }
                        Firebase.auth.currentUser!!.updateProfile(profileUpdates)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Log.d("TAG", "User profile updated.")
                                }
                            }
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

