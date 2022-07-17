package com.georgian.farmington

import android.content.ContentValues
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
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.georgian.farmington.LoginActivity as LoginActivity1


class LoginActivity : AppCompatActivity() {


    private lateinit var binding : ActivityLoginBinding
    private lateinit var user: FirebaseAuth
    private lateinit var googleSignInClient : GoogleSignInClient

    //                  GOOGLE SIGN IN
    var mGoogleSignInClient: GoogleSignInClient? = null
    val RC_SIGN_IN = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

//              SIGN IN WITH GOOGLE START
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


//        SIGN IN WITH GOOGLE END

        this.supportActionBar?.title = "Farmington - Login"
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = FirebaseAuth.getInstance()

        binding.btloginu.setOnClickListener({
            LoginUser()
        })

        binding.signInButton.setOnClickListener {
            signIn();
        }

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

//                      SIGN IN WITH GOOGLE FUN START

    private fun signIn() {
        val signinIntent: Intent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(signinIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            firebaseAuthWithGoogle(account);
            account.email?.let { createSharedPref(it) };

            // Signed in successfully, show authenticated UI.
            Toast.makeText(this, "Sign in successfull", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(ContentValues.TAG, "signInResult:failed code=" + e.statusCode)
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        user = Firebase.auth
        user.signInWithCredential(credential)
            .addOnSuccessListener(this) { authResult ->
                startActivity(Intent(Intent(this, HomeActivity::class.java)))
            }
            .addOnFailureListener(this) { e ->
                Toast.makeText(
                    this, "Authentication failed.",
                    Toast.LENGTH_SHORT
                ).show()
            }
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
                        createSharedPref(email);
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

    private fun createSharedPref(email: String) {
        var pref = getSharedPreferences("pref", MODE_PRIVATE)
        var edit = pref.edit()
        edit.putString("email", email)
        edit.commit()
    }


}