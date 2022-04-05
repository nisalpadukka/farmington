package com.georgian.farmington

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.georgian.farmington.databinding.ActivityForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding : ActivityForgotPasswordBinding
    private lateinit var user: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        user = FirebaseAuth.getInstance()

        binding.btnReset.setOnClickListener({
            pwdReset()
        })
    }

    private fun pwdReset() {
        val pwdreset = binding.pwdReset.getText().toString().trim()

        if (pwdreset.isNotEmpty()) {
            user.sendPasswordResetEmail(pwdreset)
                .addOnCompleteListener(ForgotPasswordActivity()) { task ->

                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Check your Email to reset your password ", Toast.LENGTH_SHORT)
                            .show()
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, task.exception!!.message, Toast.LENGTH_SHORT)
                            .show()
                    }

                }
                .addOnFailureListener(ForgotPasswordActivity()){ task ->
                    Toast.makeText(this, "failure", Toast.LENGTH_SHORT)
                        .show()
                }
        } else {
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onBackPressed() {
        var intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
        super.onBackPressed()
    }
}


