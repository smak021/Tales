package com.example.tale.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StoryViewModel:ViewModel() {
    val position = MutableLiveData<Int>()
    init {
        position.value=0
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
}