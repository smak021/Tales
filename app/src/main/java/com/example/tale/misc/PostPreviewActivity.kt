package com.example.tale.misc

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.example.tale.MainActivity
import com.example.tale.R
import com.example.tale.model.storageRef
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_post_preview.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class PostPreviewActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_preview)
        val user = FirebaseAuth.getInstance().currentUser
        val email = user?.email
        var loc: Uri? = null
        var exten: String? = null
        val time = Calendar.getInstance().time
        val pickImage = registerForActivityResult(ActivityResultContracts.GetContent())
        { uri ->
            if (uri != null) {
                imagePreview.setImageURI(uri)
                loc = uri
                val getName = uri.getName(this)
                exten = getName?.substring(getName.lastIndexOf("."))
            } else {
                finish()
            }
        }
        pickImage.launch("image/*")

        postButton.setOnClickListener {
            circular_progress.visibility = View.VISIBLE

            val randomNumber: Int = Random().nextInt(100000)
            val dateTime = LocalDateTime.now()
            val tim = dateTime.format(DateTimeFormatter.ofPattern("ydMHmss"))
            val filename = "IMG_${randomNumber}_$tim$exten"

            addStorage(loc, filename, email, time)
        }
    }

    //function to add images to cloud storage
    private fun addStorage(uri: Uri?, filename: String, uid: String?, time: Date) {
        if (uri != null) {
            val loc = storageRef.child("Stories/$filename")
            val ref = FirebaseStorage.getInstance().getReferenceFromUrl(loc.toString())

            loc.putFile(uri).addOnCompleteListener {
                circular_progress.visibility = View.INVISIBLE
                Log.d(TAG, "Image Added Succesfully")
                Toast.makeText(this, "Uploaded Succesfully", Toast.LENGTH_LONG).show()

                ref.downloadUrl.addOnSuccessListener {
                    val url = it.toString()
                    adddata(uid, time, url)

                }


            }
                .addOnFailureListener {
                    Log.w(TAG, "Image Uploading failed")

                }
        }
    }

    //Getting file name from uri source
    private fun Uri.getName(context: Context): String? {
        val returnCursor = context.contentResolver.query(this, null, null, null, null)
        val nameIndex: Int? = returnCursor?.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor?.moveToFirst()
        val fileName = returnCursor?.getString(nameIndex!!)
        returnCursor?.close()
        return fileName
    }

    //Adding story to DB
    private fun adddata(uid: String?, time: Date, filename: String?) {
        val db = Firebase.firestore
        val storydate = hashMapOf(
            "url" to filename,
            "time" to time
        )
        if (uid != null) {
            db.collection("stories").document(uid).collection("user_stories").add(storydate)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "Document added ID:$documentReference")
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                .addOnFailureListener { e -> Log.w(TAG, "Error adding document", e)
                    Toast.makeText(this, "Upload Failed.", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
