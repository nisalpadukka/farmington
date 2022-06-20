package com.georgian.farmington

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.georgian.farmington.databinding.ActivityNewProductBinding;
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.*
import java.util.UUID.randomUUID

class NewProductActivity : AppCompatActivity() {
    lateinit var binding: ActivityNewProductBinding
    lateinit var ImageUri: Uri
    lateinit var productId:UUID
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_product)

        this.supportActionBar?.title = "Farmington - Market Place"

        val displayNameText : EditText = findViewById(R.id.productName)
        val emailText : EditText = findViewById(R.id.productDes)
        val oldPassText : EditText = findViewById(R.id.price)
        val newPassText : EditText = findViewById(R.id.quantity)

        binding = ActivityNewProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productId = randomUUID();

        val user = Firebase.auth.currentUser
        if (user != null) {
            Log.d("Check","User is signed in")
        } else {
            Toast.makeText(this, "Please Sign In again", Toast.LENGTH_SHORT)
                .show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }

        //UPDATE INFO
        val updateName: Button = findViewById (R.id.publishListing)
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
        val productName : EditText = findViewById(R.id.productName)
        val productDes : EditText = findViewById(R.id.productDes)
        val price : EditText = findViewById(R.id.price)
        val quantity : EditText = findViewById(R.id.quantity)

        if(productName.text.isEmpty()){
            Toast.makeText(this, "Product Name cannot be empty", Toast.LENGTH_SHORT)
                .show()
            return;
        }

        if(productDes.text.isEmpty()){
            Toast.makeText(this, "Product Description cannot be empty", Toast.LENGTH_SHORT)
                .show()
            return;
        }

        if(quantity.text.isEmpty()){
            Toast.makeText(this, "Quantity cannot be empty", Toast.LENGTH_SHORT)
                .show()
            return;
        }

        if(price.text.isEmpty()){
            Toast.makeText(this, "Price cannot be empty", Toast.LENGTH_SHORT)
                .show()
            return;
        }

        val productData = hashMapOf(
            "product_id" to productId.toString(),
            "product_name" to productName.text.toString(),
            "description" to productDes.text.toString(),
            "price" to price.text.toString(),
            "quantity" to quantity.text.toString()
        )

        db = FirebaseFirestore.getInstance();
        db.collection("products").add(productData)
            .addOnSuccessListener {
                Toast.makeText(this, "Submitted successfully", Toast.LENGTH_SHORT)
                    .show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to submit", Toast.LENGTH_SHORT)
                    .show()
            }

    }

    private fun uploadImage() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading !!")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val storageRef = FirebaseStorage.getInstance().getReference("product_images/$productId")

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

    override fun onBackPressed() {
        finish()
    }
}