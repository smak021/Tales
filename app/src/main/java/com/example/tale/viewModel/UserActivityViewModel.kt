package com.example.tale.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tale.model.*
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class UserActivityViewModel:ViewModel() {

    var id= String()


    //Fetch Story Variables
    var storyData = MutableLiveData<ArrayList<StoryDetails>>()

    //Fetch followers List
    val followersLiveData = MutableLiveData<ArrayList<String>>()
    private val followersArray = ArrayList<String>()
    //Fetch following List
    val followingLiveData = MutableLiveData<ArrayList<String>>()
    private val followingArray = ArrayList<String>()
    //Fetch User Variables
    var userData = MutableLiveData<ArrayList<UserDetails>>()
    private val array= ArrayList<UserDetails>()

    fun fetchFollowersList(id:String)
    {
        followersLiveData.value?.clear()
        CoroutineScope(IO).launch {
            val db = Firebase.firestore
            db.collection("users").document(id).collection("followers")
                .addSnapshotListener { it, _ ->
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

    fun fetchFollowingList(id: String)
    {
        followingLiveData.value?.clear()
        CoroutineScope(IO).launch {
        val db = Firebase.firestore
            db.collection("users").document(id).collection("following")
                .addSnapshotListener { it, _ ->
                    followingArray.clear()
                    if (it != null) {
                        for(documents in it) {
                            if (documents.data["email"]!=null){
                            followingArray.add(documents.data["email"] as String)
                            followingLiveData.value = followingArray}
                        }
                    }
                }
        }
    }

    fun fetchUserData(id:String) {
            CoroutineScope(IO).launch {
                val db = Firebase.firestore
                db.collection("users").whereEqualTo("email", id)
                    .get().addOnSuccessListener()
                     {
                        array.clear()
                        if (it != null) {
                            for (docs in it) {
                                array.add(docs.toObject(UserDetails::class.java))
                                userData.value = array

                            }
                        }
                    }
                //println(Thread.currentThread().name.toString())
            }
        }
    fun fetchUserStory(id:String){
        storyData.value?.clear()
        CoroutineScope(IO).launch {
            val db = Firebase.firestore
            db.collection("stories").document(id)
                .collection("user_stories").orderBy("time",Query.Direction.DESCENDING).addSnapshotListener { value, _ ->
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