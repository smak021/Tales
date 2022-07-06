package com.example.tale.auth

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.tale.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val db = Firebase.firestore

        registerButton.setOnClickListener{
            val userDetails = hashMapOf(
                "full_name" to nameET.text.toString().trim(),
                "email" to emailET.text.toString().trim(),
                "phone" to phoneET.text.toString(),
                "isVerified" to false
            )
            //Check empty box

            //Check for password

            //check confirmation password

            //valid mobile

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailET.text.toString().trim(), passET.text.toString().trim())
                .addOnCompleteListener(this) {
                    if(it.isSuccessful)
                {

                        successLT.visibility = View.VISIBLE
                        successLT.playAnimation()
                        println(nameET.text.toString().trim())
                        db.collection("users").document(emailET.text.toString()).set(userDetails)
                        .addOnSuccessListener {
                            Log.d(TAG, "Successfully added to DB")
                        }
                        .addOnFailureListener {
                            Log.w(TAG, "Failure")
                        }
                    Toast.makeText(this, "Successfully Registered",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,ProfilePictureActivity::class.java))
                    finish()
                }
                else
                {
                    Log.w(TAG, "createUserWithEmail:failure", it.exception)
                    Toast.makeText(baseContext, it.exception?.message.toString(),
                        Toast.LENGTH_SHORT).show()
                }


            }
                .addOnFailureListener {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT)
                }
        }
    }

}