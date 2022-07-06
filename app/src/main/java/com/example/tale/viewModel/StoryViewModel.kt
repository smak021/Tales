package com.example.tale.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class StoryViewModel:ViewModel() {
    val url = MutableLiveData<String>()
    val position = MutableLiveData<Int>()
    init {
        position.value=0
    }

    fun initPosition(i:Int)
    {
        position.value=i
    }

    fun updateData(){

    }
    fun incrementData() {
        position.value = position.value?.plus(1)

    }
    fun decrementData(){
            position.value = position.value?.minus(1)
    }
    fun resetData(){
        position.value = 0
    }
    fun getUserData(name:String){
        val db=Firebase.firestore
        db.collection("users").document(name).get().addOnSuccessListener {
            if (it.data?.get("userPic") !=null) {
                url.value = it.data?.get("userPic").toString()
            }

        }

    }

    fun bgProgress(){

    }
}