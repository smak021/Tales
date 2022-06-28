package com.example.tale.misc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tale.R
import com.example.tale.adapters.GalleryAdapter
import com.example.tale.viewModel.GalleryViewModel
import kotlinx.android.synthetic.main.activity_gallery.*

class GalleryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)


        val viewModel = ViewModelProvider(this)[GalleryViewModel::class.java]
        galleryRecycler.layoutManager= GridLayoutManager(this,4,GridLayoutManager.VERTICAL,false)
        viewModel.loadPath(applicationContext)
        viewModel.imagePath.observe(this) {
            galleryRecycler?.adapter = GalleryAdapter(this, it)
        }

    }
}