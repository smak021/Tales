package com.example.tale.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tale.model.StoryDetails
import com.example.tale.model.UserDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class HomeFragmentViewModel : ViewModel() {
    var testData = MutableLiveData<LinkedHashMap<UserDetails, ArrayList<StoryDetails>>>()
    private val map = LinkedHashMap<UserDetails, ArrayList<StoryDetails>>()
    private val uid = FirebaseAuth.getInstance().currentUser?.email
    private val db = Firebase.firestore
    var span = MutableLiveData<Int>()
    //var userDetail=UserDetails()

    //fetchName

    fun updateRecyclerSpan(value: Int)
    {
        span.value=value
    }

    fun fetchStory() {
        CoroutineScope(IO).launch {
            if (uid != null) {
                db.collection("users").document(uid)
                    .collection("following").addSnapshotListener { it, error ->
                        testData.value?.clear()
                        map.clear()
                        if (it != null) {
                            for (docs in it) {
                                if (docs.data["email"] != null) {
                                    val id: String = docs.data["email"] as String
                                    db.collection("users").document(id).get()
                                        .addOnSuccessListener { ittr ->
                                            val userDetail =
                                                ittr.toObject(UserDetails::class.java)!!
                                            db.collection("stories").document(id)
                                                .collection("user_stories")
                                                .orderBy("time", Query.Direction.ASCENDING)
                                                .addSnapshotListener() { value, err ->
                                                    val array = ArrayList<StoryDetails>()
                                                    array.clear()
                                                    if (value != null) {
                                                        for (documents in value) {

                                                            array.add(
                                                                documents.toObject(
                                                                    StoryDetails::class.java
                                                                )
                                                            )
                                                            if (array.isNotEmpty()) {
                                                                map[userDetail] = array
                                                                testData.value = map
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
    }

}
