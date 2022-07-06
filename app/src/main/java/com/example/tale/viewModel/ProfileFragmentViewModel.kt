package com.example.tale.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tale.model.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class ProfileFragmentViewModel:ViewModel() {
    //Fetch Story Variables
    var storyData = MutableLiveData<ArrayList<StoryDetails>>()
    //Fetch Followers Variables
    private val uid = FirebaseAuth.getInstance().currentUser?.email
    //Fetch followers List
    val followersLiveData = MutableLiveData<ArrayList<String>>()
    private val followersArray = ArrayList<String>()
    //Fetch following List
    private val followingArray = ArrayList<String>()
    //Fetch User Variables
    var testData = MutableLiveData<ArrayList<UserDetails>>()
    private val array= ArrayList<UserDetails>()
    fun fetchFollowersList()
    {
        followersLiveData.value?.clear()
        CoroutineScope(IO).launch {
            val db = Firebase.firestore
            if (uid != null) {
                db.collection("users").document(uid).collection("followers")
                    .addSnapshotListener(){ it,error->
                        followersArray.clear()
                        if (it != null) {
                            for(documents in it) {
                                if (documents.data["email"]!=null){
                                    followersArray.add(documents.data["email"] as String)
                                    followersLiveData.value = followersArray
                                }
                            }
                        }
                    }
            }
        }
    }

    fun fetchFollowingList()
    {
        followersL.value?.clear()
        CoroutineScope(IO).launch {
        val db = Firebase.firestore
        if (uid != null) {
            db.collection("users").document(uid).collection("following")
                .addSnapshotListener(){ it,error->
                    followingArray.clear()
                    if (it != null) {
                        for(documents in it) {
                            if (documents.data["email"]!=null){
                            followingArray.add(documents.data["email"] as String)
                            followersL.value = followingArray}
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
    fun fetchUserStory(){
        storyData.value?.clear()
        CoroutineScope(IO).launch {
            val db = Firebase.firestore
            if (uid != null) {
                db.collection("stories").document(uid)
                    .collection("user_stories").orderBy("time",Query.Direction.DESCENDING).addSnapshotListener { value, error ->
                        val array = ArrayList<StoryDetails>()
                        array.clear()
                        if (value != null) {
                            for (documents in value) {

                                array.add(documents.toObject(StoryDetails::class.java))
                                storyData.value = array
                            }
                        }

                }
            }

        }
    }
}