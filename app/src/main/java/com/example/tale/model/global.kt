package com.example.tale.model

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

var followersL = MutableLiveData<ArrayList<String>>()
val followersList = mutableListOf<Any>()
var story_list = arrayListOf<Any>()
var storageRef = Firebase.storage.reference
//val uid = FirebaseAuth.getInstance().currentUser?.email
