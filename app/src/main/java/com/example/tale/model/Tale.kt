package com.example.tale.model

import android.content.ContentValues
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


object Tale {
    private val user= FirebaseAuth.getInstance().currentUser?.email.toString()
    private val db = Firebase.firestore
    private var userDetails = UserDetails()

    fun follow(userToFollow:String)
    {
        CoroutineScope(IO).launch {
        val data = hashMapOf(
            "email" to userToFollow
        )
        val revData = hashMapOf(
            "email" to user
        )
        db.collection("users").document(userToFollow)
            .collection("followers").add(revData).addOnSuccessListener {
                Log.d(ContentValues.TAG, "Success")
            }
        db.collection("users").document(user).collection("following")
            .add(data).addOnSuccessListener {
                Log.d(ContentValues.TAG, "Success")
            }
        }
    }



    fun unfollow(userToUnfollow:String) {
        CoroutineScope(IO).launch {
            db.collection("users").document(userToUnfollow)
                .collection("followers").whereEqualTo("email", user)
                .get().addOnSuccessListener {
                    for (docs in it) {
                        val id = docs.reference.id
                        db.collection("users").document(userToUnfollow).collection("followers")
                            .document(id).delete().addOnSuccessListener {
                                Log.d(ContentValues.TAG, "Success")
                            }
                    }
                }

            db.collection("users").document(user)
                .collection("following").whereEqualTo("email", userToUnfollow)
                .get().addOnSuccessListener {
                    for (docs in it) {
                        val id = docs.reference.id
                        db.collection("users").document(user).collection("following")
                            .document(id).delete().addOnSuccessListener {
                                Log.d(ContentValues.TAG, "Success")
                            }
                    }
                }

        }
    }
}