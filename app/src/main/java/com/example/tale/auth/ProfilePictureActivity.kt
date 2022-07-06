package com.example.tale.auth

import android.R.attr
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.tale.R
import com.example.tale.misc.GalleryActivity
import com.example.tale.model.storageRef
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_profile_picture.*
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class ProfilePictureActivity : AppCompatActivity() {
    var xtra:Uri? = null
    private lateinit var uid:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_picture)
        uid = FirebaseAuth.getInstance().currentUser?.email.toString()

        val resultLaunch = registerForActivityResult(ActivityResultContracts
            .StartActivityForResult()) {result->

            when(result.resultCode){
                Activity.RESULT_OK->{
                    xtra=result.data?.extras?.get("path") as Uri
                    Glide.with(this).load(xtra).into(profile_picIV)
                    clearIv.visibility= View.VISIBLE
                }
                Activity.RESULT_CANCELED->{
                    //Cancelled
                }


            }

        clearIv.setOnClickListener {

            Glide.with(this).load(R.drawable.add_circle).into(profile_picIV)
            xtra=null
            clearIv.visibility= View.GONE

        }


        }
        profile_pic_select.setOnClickListener {

            //startActivity(Intent(this, GalleryActivity::class.java))

          resultLaunch.launch(Intent(this, GalleryActivity::class.java))

        }
        nextIB.setOnClickListener {

            if(xtra != null)
            {
                println("UPload")

                addPhoto(xtra!!)



            }
            else{
                println("No pic")
                startActivity(Intent(this, ContactsActivity::class.java))
            }
        }
    }

    private fun addPhoto(xtra: Uri) {
        //generating random file name
        val randomNumber: Int = Random().nextInt(100000)
        val dateTime = LocalDateTime.now()
        val tim = dateTime.format(DateTimeFormatter.ofPattern("ydMHmss"))
        val filename = "IMG_${randomNumber}_$tim"
        //Adding to Firebase Storage
        val loc = storageRef.child("ProfilePictures/$filename")
        val reference = FirebaseStorage.getInstance().getReferenceFromUrl(loc.toString())
        loc.putFile(xtra).addOnCompleteListener{

            reference.downloadUrl.addOnSuccessListener {
                val url = it
                addDatatoDB(url)
            }
        }



    }

    private fun addDatatoDB(url: Uri?) {

        val db = Firebase.firestore
        val data = hashMapOf(
            "userPic" to url
        )

        db.collection("users").document(uid)
            .set(data, SetOptions.merge()).addOnSuccessListener {
                Log.d("Firestore, Profile Pic:","Added succesfully")
                startActivity(Intent(this,ContactsActivity::class.java))
                finish()
        }
            .addOnFailureListener {
                e-> Log.w(TAG, "Error writing document", e)
            }

    }


    override fun onBackPressed() {

        MaterialAlertDialogBuilder(this, com.google.android.material.R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog)
            .setTitle("Skip setting profile picture?")
            .setMessage("To change picture, tap on the image holder")
            .setNegativeButton("Skip"){ dialog,which->
                startActivity(Intent(this,ContactsActivity::class.java))
                finish()
            }
            .setPositiveButton("Continue"){ dialog, which ->

            }
            .show()
    }
}