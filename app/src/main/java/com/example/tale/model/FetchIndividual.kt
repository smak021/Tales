package com.example.tale.model

import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
val userDetails:UserDetails = UserDetails()
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


    fun fetchDetails(userid:String):UserDetails{

        val db = Firebase.firestore
        db.collection("users").whereEqualTo("email",userid).get().addOnSuccessListener {
            for (docs in it)
            {
                if (docs.data["full_name"]!=null){
                    userDetails.setfirstName(docs.data["full_name"] as String?)
                }
                if (docs.data["email"]!=null){
                    userDetails.setemail(docs.data["email"] as String)
                }
                if (docs.data["phone"]!=null){
                    userDetails.setphoneNo(docs.data["phone"] as String)
                }

            }

        }

        return userDetails
    }
}