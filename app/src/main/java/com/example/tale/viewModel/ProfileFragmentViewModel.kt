package com.example.tale.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tale.model.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class ProfileFragmentViewModel:ViewModel() {

    //Fetch Followers Variables
    private val uid = FirebaseAuth.getInstance().currentUser?.email
    private val followersArray = ArrayList<String>()
    //Fetch User Variables
    var testData = MutableLiveData<ArrayList<UserDetails>>()
    private val array= ArrayList<UserDetails>()

    fun fetchFollowersList()
    {
        followersL.value?.clear()
        CoroutineScope(IO).launch {
        val db = Firebase.firestore
        if (uid != null) {
            db.collection("users").document(uid).collection("followers")
                .addSnapshotListener(){ it,error->
                    followersArray.clear()
                    if (it != null) {
                        for(documents in it) {
                            followersArray.add(documents.data["followerEmail"] as String)
                            followersL.value = followersArray
                        }
                    }
                }
            }
        }
    }

    fun fetchUserData() {
            CoroutineScope(IO).launch {
                val db = Firebase.firestore
                db.collection("users").whereEqualTo("email", uid)
                    .get().addOnSuccessListener()
                     {
                        array.clear()
                        if (it != null) {
                            for (docs in it) {
                                array.add(docs.toObject(UserDetails::class.java))
                                testData.value = array

                            }
                        }
                    }
                println(Thread.currentThread().name.toString())
            }
        }
}