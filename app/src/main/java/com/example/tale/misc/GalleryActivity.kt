package com.example.tale.misc

import android.R.attr
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tale.R
import com.example.tale.adapters.GalleryAdapter
import com.example.tale.viewModel.GalleryViewModel
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_gallery.*
import java.io.File


class GalleryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)


        val viewModel = ViewModelProvider(this)[GalleryViewModel::class.java]
        galleryRecycler.layoutManager= GridLayoutManager(this,4,GridLayoutManager.VERTICAL,false)
        viewModel.loadPath(applicationContext)
        viewModel.imagePath.observe(this) {
            galleryRecycler?.adapter = GalleryAdapter(this, it,object:
                GalleryAdapter.OnItemClickListener{
                override fun onItemClick(position: Int, path: String) {
                    CropImage.activity(Uri.fromFile(File(path)))
                        .setFixAspectRatio(true)
                        .start(this@GalleryActivity)
                    //setResult(RESULT_OK, Intent().putExtra("path",path))
                    //println("Activity test: Pass")
                }

            })

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                val resultUri = result.uri
                //println(resultUri)
                setResult(RESULT_OK, Intent().putExtra("path",resultUri))
                finish()
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }
    }
}