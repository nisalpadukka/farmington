package com.georgian.farmington

import android.content.Context
import android.content.Context.*
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.georgian.farmington.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.georgian.farmington.LoginActivity as LoginActivity1

class LoginActivity : AppCompatActivity() {


    private lateinit var binding : ActivityLoginBinding
    private lateinit var user: FirebaseAuth
    private lateinit var googleSignInClient : GoogleSignInClient
    val RC_SIGN_IN = 100
    val TAG = "GOOGLE_SIGN_TAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        this.supportActionBar?.title = "Farmington - Login"
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

    private fun checkUser(){
        val firebaseUser = user.currentUser
        if(firebaseUser != null){
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun LoginUser() {
        val email = binding.etxtEmailLogin.getText().toString().trim()
        val pwd = binding.eTxtPwdLogin.getText().toString().trim();

        if (email.isNotEmpty() && pwd.isNotEmpty()) {
            user.signInWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(LoginActivity1()) { task ->

                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT)
                            .show()

                        // onetime login logic
                        var pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
                        var edit = pref.edit()
                        edit.putString("email",email)
                        edit.commit()

                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, task.exception!!.message, Toast.LENGTH_SHORT)
                            .show()
                    }

                }
                .addOnFailureListener(LoginActivity1()){ task ->
                    Toast.makeText(this, "failure", Toast.LENGTH_SHORT)
                        .show()
                }
        } else {
            Toast.makeText(this, "Enter username and password", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Log.d(TAG,"onActivityResult: Google SignIn Intent Result")
            val accountTask = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = accountTask.getResult(ApiException::class.java)
                firebaseAuthWithGoogleAccount(account)

            } catch (e: Exception) {
                Log.d(TAG,"onActivityResult: ${e.message}")
            }

        }
    }

    private fun firebaseAuthWithGoogleAccount(account: GoogleSignInAccount?) {
        Log.d(TAG,"firebaseAuthWithGoogleAccount: begin firebae auth with google account")
        val credential = GoogleAuthProvider.getCredential(account!!.idToken,null)
        user.signInWithCredential(credential)
            .addOnSuccessListener {authResult ->
                Log.d(TAG,"firebaseAuthWithGoogleAccount: Logged in")

                val firebaseUser = user.currentUser
                val uid = firebaseUser!!.uid
                val email = firebaseUser!!.email

               Log.d(TAG,"firebaseAuthWithGoogleAccount: email${email}, uid${uid}")
                if(authResult.additionalUserInfo!!.isNewUser){

                    Log.d(TAG,"firebaseAuthWithGoogleAccount: Account Created!!! \n $email")
                    Toast.makeText(this,"Account Created!!! \n$email",Toast.LENGTH_LONG).show()
                }else{
                    Log.d(TAG,"firebaseAuthWithGoogleAccount: Existing User!!! \n $email")
                    Toast.makeText(this,"logged in!!! \n$email",Toast.LENGTH_LONG).show()
                }

                startActivity(Intent(this, HomeActivity::class.java))
                finish()

            }.addOnFailureListener { e->
                Log.d(TAG,"firebaseAuthWithGoogleAccount: Existing User!!! Login Failed")
                Toast.makeText(this,"login Failed!!!",Toast.LENGTH_LONG).show()

            }
    }
}