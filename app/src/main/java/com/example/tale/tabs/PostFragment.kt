package com.example.tale.tabs


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.tale.misc.PostPreviewActivity
import com.google.android.material.tabs.TabLayout
import com.example.tale.R
import com.example.tale.misc.GalleryActivity
import com.example.tale.model.CustomLifeCycle
import kotlinx.android.synthetic.main.fragment_post.*
import kotlinx.android.synthetic.main.fragment_post.view.*
import java.io.File


class PostFragment : Fragment() {
    private val customLifecycleOwner = CustomLifeCycle()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        customLifecycleOwner.attachLifecycleOwner(this)
        // Inflate the layout for this fragment
        val postView= inflater.inflate(R.layout.fragment_post, container, false)

        val cardView = postView.findViewById<com.google.android.material.card.MaterialCardView>(R.id.pic_select)
        cardView.setOnClickListener{
           val intent= Intent(context, GalleryActivity::class.java)
            startActivity(intent)
        }
        val cameraProviderFuture = ProcessCameraProvider.getInstance(postView.context)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            bindPreview(cameraProvider,postView)
        },ContextCompat.getMainExecutor(postView.context))


        return  postView

    }


    private fun bindPreview(cameraProvider: ProcessCameraProvider?,view:View) {
        val imageCapture = ImageCapture.Builder()
            .setTargetRotation(view.display.rotation)
            .build()

        val preview = Preview.Builder().build()
        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()

        preview.setSurfaceProvider(cameraPreviewView.surfaceProvider)
        var camera = cameraProvider?.bindToLifecycle(viewLifecycleOwner, cameraSelector,imageCapture, preview)
        //button
        view.cameraButton.setOnClickListener {
            val outputFileOptions = ImageCapture.OutputFileOptions.Builder(File("${context?.externalCacheDir}/IMG-${System.currentTimeMillis()}.JPEG")).build()
            imageCapture.takePicture(outputFileOptions,ContextCompat.getMainExecutor(view.context)
                ,object : ImageCapture.OnImageSavedCallback{
                    override fun onError(exception: ImageCaptureException) {
                        Log.d("Camera Capture Error:", exception.toString())
                    }

                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        val uri = outputFileResults.savedUri
                        val path = uri?.path
                        startActivity(Intent(context, PostPreviewActivity::class.java).putExtra("path",path))
                        println(path)

                    }
                })
        }

    }


    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).findViewById<TabLayout>(R.id.tabLayout).visibility = View.GONE
     //  val clayout=(activity as AppCompatActivity).findViewById<ViewGroup>(R.id.viewPager)
      // clayout?.layoutTransition?.enableTransitionType(
        //    LayoutTransition.CHANGING)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()


    }

}