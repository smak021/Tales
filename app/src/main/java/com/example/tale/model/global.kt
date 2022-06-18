package com.example.tale.model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

var mapp= LinkedHashMap<String,MutableList<Any>>()
val followersList = mutableListOf<Any>()
var story_list = arrayListOf<Any>()
var storageRef = Firebase.storage.reference
val uid = FirebaseAuth.getInstance().currentUser?.email
