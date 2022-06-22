package com.example.tale.model

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
var userData = MutableLiveData<ArrayList<UserDetails>>()
val array= ArrayList<UserDetails>()
val userDetails: UserDetails = UserDetails()
var followers:MutableList<FollowersDetails>? = mutableListOf()

object FetchIndividual {
    fun fetchStory(userid:String){
        val db = Firebase.firestore
        db.collection("stories").document(userid).collection("user_stories")
            .orderBy("time", Query.Direction.DESCENDING).get().addOnSuccessListener {
            for (docs in it)
            {
                docs.data["url"]?.let { it1 -> story_list.add(it1) }
            }
        }
    }

    fun fetchFollowers(userid:String):MutableList<FollowersDetails>?{
        val db = Firebase.firestore
        db.collection("users").document(userid).collection("followers")
            .get().addOnSuccessListener {
            followers?.clear()
                for (docs in it)
                {
                    val followerDet =FollowersDetails()
                    followerDet.setfollowerName(docs.data["name"].toString())
                    followerDet.setfollowerEmail(docs.data["email"].toString())
                    followers?.add(followerDet)

                }

            }
        return  followers
    }


    fun fetchDetails(userid:String):MutableLiveData<ArrayList<UserDetails>>{
        val db = Firebase.firestore
        db.collection("users").whereEqualTo("email",userid).get().addOnSuccessListener {

            for (docs in it)
            {
               array.add(docs.toObject(UserDetails::class.java))
                userData.value= array

            }

        }

        return userData
    }
}