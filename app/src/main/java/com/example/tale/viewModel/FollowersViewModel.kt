package com.example.tale.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tale.model.UserDetails
import com.example.tale.model.followersL
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FollowersViewModel:ViewModel() {

    val friends = MutableLiveData<ArrayList<UserDetails>>()
        private val array = ArrayList<UserDetails>()

    fun fetchFriends(){
        val db = Firebase.firestore
        for ( value in followersL.value!!) {
            println(value)
            db.collection("users").whereEqualTo("email",value)
                .get().addOnSuccessListener {

                for ( docs in it)
                {
                    array.add(docs.toObject(UserDetails::class.java))
                    friends.value = array

                }

            }

        }
    }
}