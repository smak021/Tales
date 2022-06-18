package com.example.tale.model



import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase




object FollowCheck {


    private val user = FirebaseAuth.getInstance().currentUser
    fun followers() {
        val db = Firebase.firestore
        db.collection("users").document(user?.email.toString())
            .collection("followers")
            .addSnapshotListener { value, err ->
                followersList.clear()



                for (doc in value!!) {

                    if (doc.data["email"] != null) {
                        doc.data["email"]?.let {
                            followersList.add(it)

                            db.collection("stories").document(it as String)
                                .collection("user_stories")
                                .orderBy("time", Query.Direction.DESCENDING)
                                .addSnapshotListener { valu, error ->
                                    val lis = mutableListOf<Any>()
                                    lis.clear()
                                    for (document in valu!!) {


                                        lis.add(document.data["url"] as String)
                                        //  println(lis)
                                        mapp[it] = lis


                                    }

                                }
                        }

                    }

                }

            }
    }
}