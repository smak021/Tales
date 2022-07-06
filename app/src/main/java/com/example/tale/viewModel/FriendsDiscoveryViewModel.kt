package com.example.tale.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tale.model.UserDetails
import com.example.tale.model.followersL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FriendsDiscoveryViewModel:ViewModel() {
    val userData = MutableLiveData<ArrayList<UserDetails>>()
    private val array = ArrayList<UserDetails>()

    fun getData(){
        val db = Firebase.firestore
        val user= FirebaseAuth.getInstance().currentUser?.email.toString()
       // println(followersL.value?.get(0))
        db.collection("users").get().addOnSuccessListener {
            array.clear()
            for(docs in it)
            {
                if(followersL.value != null) {
                    if (!followersL.value!!.contains(docs.data["email"]) && docs.data["email"]!=user) {
                        array.add(docs.toObject(UserDetails::class.java))
                        userData.value = array
                    }
                }else if (docs.data["email"]!=user){
                    array.add(docs.toObject(UserDetails::class.java))
                    userData.value = array
                }
            }

        }
    }

}