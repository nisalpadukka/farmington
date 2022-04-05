package com.georgian.farmington

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.georgian.farmington.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var user: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        user = FirebaseAuth.getInstance()

        binding.btloginu.setOnClickListener({
            LoginUser()
        })

        // on click of forgot password user will be redirected to forgot password screen
        var forgotPwd = findViewById<TextView>(R.id.forgotPwd)
        forgotPwd.setOnClickListener({
            var intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
            finish()
        })

        // on click of forgot password user will be redirected to forgot password screen
        var regTxt = findViewById<TextView>(R.id.txtReg)
        regTxt.setOnClickListener({
            var intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
            finish()
        })
    }
    private fun LoginUser() {
        val email = binding.etxtEmailLogin.getText().toString().trim()
        val pwd = binding.eTxtPwdLogin.getText().toString().trim();

        if (email.isNotEmpty() && pwd.isNotEmpty()) {
            user.signInWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(LoginActivity()) { task ->

                    if (task.isSuccessful()) {
                        Toast.makeText(this, "user added successfully", Toast.LENGTH_SHORT)
                            .show()
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, task.exception!!.message, Toast.LENGTH_SHORT)
                            .show()
                    }

                }
                .addOnFailureListener(LoginActivity()){ task ->
                    Toast.makeText(this, "failure", Toast.LENGTH_SHORT)
                        .show()
                }
        } else {
            Toast.makeText(this, "Enter username and password", Toast.LENGTH_SHORT).show()
        }
    }
}