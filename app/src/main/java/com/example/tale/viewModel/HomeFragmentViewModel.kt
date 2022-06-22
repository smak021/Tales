package com.example.tale.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tale.model.FollowCheck
import com.example.tale.model.StoryDetails
import com.example.tale.model.UserDetails
import com.example.tale.model.uid
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class HomeFragmentViewModel:ViewModel() {
    var testData = MutableLiveData<LinkedHashMap<String,ArrayList<StoryDetails>>>()
    private val map = LinkedHashMap<String,ArrayList<StoryDetails>>()

    fun fetchStory() {
        CoroutineScope(IO).launch {
        val db = Firebase.firestore
        if (uid != null) {
            db.collection("users").document(uid).collection("followers").addSnapshotListener { it, error ->
                    if (it != null) {
                        for (docs in it) {
                            if (docs.data["followerEmail"] != null) {
                                val id: String = docs.data["followerEmail"] as String
                                db.collection("stories").document(id)
                                    .collection("user_stories")
                                    .orderBy("time", Query.Direction.DESCENDING)
                                    .addSnapshotListener() { value, err ->
                                        val array = ArrayList<StoryDetails>()
                                        array.clear()
                                        if (value != null) {
                                            for (documents in value) {

                                                array.add(documents.toObject(StoryDetails::class.java))
                                                map[id] = array
                                                testData.value = map
                                                println("Hel:${testData.value!!.values} ${testData.value!!.keys}")
                                            }
                                        }

                                    }

                            }
                        }
                    }
                }

        }
    }
    }

}