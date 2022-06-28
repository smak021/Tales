package com.example.tale.viewModel

import android.content.Context
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class GalleryViewModel:ViewModel() {

    val imagePath = MutableLiveData<ArrayList<String>>()
    private val arrPath = ArrayList<String>()

    fun loadPath(applicationContext:Context) {
       viewModelScope.launch {
            arrPath.clear()
            val projection = arrayOf(MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID)
            val sortOrder = "${MediaStore.Images.Media._ID} DESC"
            applicationContext.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
                null, null, sortOrder
            )?.use { cursor ->
                val dataColIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                while (cursor.moveToNext()) {
                    val path = cursor.getString(dataColIndex)
                    arrPath.add(path)
                    imagePath.value = arrPath
                    Log.i("PATH", path)
                }

            }
        }
    }

}