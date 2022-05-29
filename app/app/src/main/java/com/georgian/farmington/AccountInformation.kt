package com.georgian.farmington

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.*
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.georgian.farmington.databinding.ActivityAccountInformationBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.io.File
import java.lang.Float.min
import kotlin.math.min


class AccountInformation : AppCompatActivity() {
    lateinit var binding: ActivityAccountInformationBinding
    lateinit var ImageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_information)

        this.supportActionBar?.title = "Farmington - Edit Profile"

        val displayNameText : EditText = findViewById(R.id.editdisplayName)
        val emailText : EditText = findViewById(R.id.editdisplayEmail)
        val oldPassText : EditText = findViewById(R.id.editdisplayOldPassword)
        val newPassText : EditText = findViewById(R.id.editdisplayNewPassword)

        binding = ActivityAccountInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = Firebase.auth.currentUser
        if (user != null) {
            Log.d("Check","User is signed in")
        } else {
            Toast.makeText(this, "Please Sign In again", Toast.LENGTH_SHORT)
                .show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }

        //PROFILE IMAGE DISPLAY
        val storageRef = Firebase.storage.reference

        // Create a reference with an initial file path and name
        val pathReference = storageRef.child("image/"+ user?.uid.toString())

        val localFile = File.createTempFile(user?.uid.toString(), "png")
        val profileImgImageView : ImageView = findViewById(R.id.profileImg)
        pathReference.getFile(localFile).addOnSuccessListener {
            // Local temp file has been created

            val bitmap: Bitmap? = BitmapFactory.decodeFile(localFile.absolutePath)

            bitmap?.cropCircularArea(700)?.apply {
                profileImgImageView.setImageBitmap(this)
            }
        }.addOnFailureListener {
            // Handle any errors

            profileImgImageView.setImageDrawable(getResources().getDrawable(R.drawable.default_profile))

        }


        displayNameText.setText("  "+Firebase.auth.currentUser?.displayName.toString())
        emailText.setText("  "+Firebase.auth.currentUser?.email.toString())

        //UPDATE INFO
        val updateName: Button = findViewById (R.id.updateInfo)
        updateName.setOnClickListener(){
            updateInfo()
        }



        //UPDATE IMAGE
        binding.selectImage.setOnClickListener{
            selectImage()
        }
        binding.uploadImage.setOnClickListener{
            uploadImage()
        }

    }
    private  fun updateInfo(){
        val displayNameText : EditText = findViewById(R.id.editdisplayName)
        val emailText : EditText = findViewById(R.id.editdisplayEmail)
        val oldPassText : EditText = findViewById(R.id.editdisplayOldPassword)
        val newPassText : EditText = findViewById(R.id.editdisplayNewPassword)

        if(displayNameText.text.isNotEmpty()){
            updateNameFun()
        }
        if(emailText.text.isNotEmpty()){
            updateEmailFun()
        }
        if(newPassText.text.isNotEmpty()){
            updatePassword()
        }


    }

    private fun uploadImage() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading !!")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val filename = Firebase.auth.currentUser?.uid.toString()
        val storageRef = FirebaseStorage.getInstance().getReference("image/$filename")

        storageRef.putFile(ImageUri).addOnSuccessListener {
            Toast.makeText(this, "Successfully Uploaded", Toast.LENGTH_SHORT)
                .show()
            if(progressDialog.isShowing){
                progressDialog.dismiss()
            }
        }.addOnFailureListener{
            if(progressDialog.isShowing){
                progressDialog.dismiss()
            }
            Toast.makeText(this, "Failed to Upload !! Please try again", Toast.LENGTH_SHORT)
                .show()
        }
        finish()
        val intent = Intent(this, ProfilePageActivity::class.java)
        startActivity(intent)
    }

    private fun updateNameFun(){
        val displayNameText : EditText = findViewById(R.id.editdisplayName)
        val emailText : EditText = findViewById(R.id.editdisplayEmail)
        val oldPassText : EditText = findViewById(R.id.editdisplayOldPassword)
        val newPassText : EditText = findViewById(R.id.editdisplayNewPassword)
        if(displayNameText.text.toString() != ""){
            val profileUpdates = userProfileChangeRequest {
                displayName = displayNameText.text.toString()
            }
            Firebase.auth.currentUser!!.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("TAG", "User profile updated.")
                    }
                }
        }
        else{
            Toast.makeText(this, "User Name can not be Empty", Toast.LENGTH_SHORT)
                .show()
        }
        finish()
        val intent = Intent(this, ProfilePageActivity::class.java)
        startActivity(intent)
    }

    private  fun updateEmailFun() {
        val displayNameText : EditText = findViewById(R.id.editdisplayName)
        val emailText : EditText = findViewById(R.id.editdisplayEmail)
        val oldPassText : EditText = findViewById(R.id.editdisplayOldPassword)
        val newPassText : EditText = findViewById(R.id.editdisplayNewPassword)
        if(emailText.text.toString() != ""){
            Firebase.auth.signInWithEmailAndPassword(Firebase.auth.currentUser?.email.toString(),oldPassText.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success now update email
                        Firebase.auth.currentUser!!.updateEmail(emailText.text.toString())
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(this, "Email Successfully Updated", Toast.LENGTH_SHORT)
                                        .show()
                                } else {
                                    Toast.makeText(this, "Error Encountered", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                    } else {
                        Toast.makeText(this, "Wrong Email or Password !! TRY AGAIN", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }
        else{
            Toast.makeText(this, "New Email can not be Empty", Toast.LENGTH_SHORT)
                .show()
        }
        finish()
        val intent = Intent(this, ProfilePageActivity::class.java)
        startActivity(intent)
    }

    private fun updatePassword(){
        val displayNameText : EditText = findViewById(R.id.editdisplayName)
        val emailText : EditText = findViewById(R.id.editdisplayEmail)
        val oldPassText : EditText = findViewById(R.id.editdisplayOldPassword)
        val newPassText : EditText = findViewById(R.id.editdisplayNewPassword)
        if(newPassText.text.toString() != ""){
            Firebase.auth.signInWithEmailAndPassword(Firebase.auth.currentUser?.email.toString(),oldPassText.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success now update email
                        Firebase.auth.currentUser!!.updatePassword(newPassText.text.toString())
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(this, "Password Successfully Updated", Toast.LENGTH_SHORT)
                                        .show()
                                } else {
                                    Toast.makeText(this, "Error Encountered", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                    } else {
                        Toast.makeText(this, "Wrong Email or Password !! TRY AGAIN", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }
        else{
            Toast.makeText(this, "New Email can not be Empty", Toast.LENGTH_SHORT)
                .show()
        }
        finish()
        val intent = Intent(this, ProfilePageActivity::class.java)
        startActivity(intent)
    }

    private fun selectImage() {

        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent,100)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==100 && resultCode== RESULT_OK){
            ImageUri = data?.data!!

            binding.profileImg.setImageURI(ImageUri)
        }
    }

    fun Bitmap.cropCircularArea(
        diameter:Int = min(width,height)
    ): Bitmap?{
        val bitmap = Bitmap.createBitmap(
            width, // width in pixels
            height, // height in pixels
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)

        // create a circular path
        val path = Path()
        val length = min(diameter, min(width,height))
        val radius = length / 2F // in pixels
        path.apply {
            addCircle(
                width/2f,
                height/2f,
                radius,
                Path.Direction.CCW
            )
        }

        // draw circular bitmap on canvas
        canvas.clipPath(path)
        canvas.drawBitmap(this,0f,0f,null)

        val x = (width - length)/2
        val y = (height - length)/2

        // return cropped circular bitmap
        return Bitmap.createBitmap(
            bitmap, // source bitmap
            x, // x coordinate of the first pixel in source
            y, // y coordinate of the first pixel in source
            length, // width
            length // height
        )
    }

    override fun onBackPressed() {
        finish()
    }




}