package com.example.tale.tabs


import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.Fragment
import com.example.tale.R
import com.example.tale.misc.GalleryBottomSheet
import com.example.tale.misc.PostPreviewActivity
import com.example.tale.model.CustomLifeCycle
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_post.*
import kotlinx.android.synthetic.main.fragment_post.view.*
import java.io.File
import kotlin.math.abs


class PostFragment : Fragment(),GestureDetector.OnGestureListener {
    private val SWIPE_MIN_DISTANCE = 120
    private val SWIPE_MAX_OFF_PATH = 250
    private val SWIPE_THRESHOLD_VELOCITY = 200
    private val bottomSheetFragment = GalleryBottomSheet()
    private val customLifecycleOwner = CustomLifeCycle()
    private lateinit var mDetector: GestureDetectorCompat
    private lateinit var layout: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        customLifecycleOwner.attachLifecycleOwner(this)
        // Inflate the layout for this fragment
        val postView= inflater.inflate(R.layout.fragment_post, container, false)

        val cardView = postView.findViewById<com.google.android.material.card.MaterialCardView>(R.id.pic_select)
        cardView.setOnClickListener{
            bottomSheetFragment.show(childFragmentManager, "BottomSheetFragment")
        }
        permissionCheck(postView)


        mDetector = GestureDetectorCompat(postView.context,this)




        return  postView

    }



    private fun permissionCheck(postView: View) {

        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    postView.permissionInfo.visibility= View.GONE
                    startCamera(postView)
                } else {
                    postView.permissionInfo.visibility= View.VISIBLE

                }
            }

        when {
            ContextCompat.checkSelfPermission(
                context!!,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                postView.permissionInfo.visibility= View.GONE
                startCamera(postView)
            }
            shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA) -> {
            // In an educational UI, explain to the user why your app requires this
            // permission for a specific feature to behave as expected. In this UI,
            // include a "cancel" or "no thanks" button that allows the user to
            // continue using your app without granting the permission.
                requestPermissionLauncher.launch(
                    android.Manifest.permission.CAMERA)
        }
            else -> {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                requestPermissionLauncher.launch(
                    android.Manifest.permission.CAMERA)
            }
        }
    }

    fun toggleCamera(cameraController:LifecycleCameraController) {

        if (cameraController.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA
            && cameraController.hasCamera(CameraSelector.DEFAULT_FRONT_CAMERA)) {
                cameraController.cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
            } else if (cameraController.cameraSelector == CameraSelector.DEFAULT_FRONT_CAMERA
            && cameraController.hasCamera(CameraSelector.DEFAULT_BACK_CAMERA)) {
                cameraController.cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            }
    }

    private fun startCamera(postView: View) {


        val cameraControl = LifecycleCameraController(postView.context);
        cameraControl.bindToLifecycle(viewLifecycleOwner)
        postView.cameraPreviewView.controller = cameraControl
        cameraControl.isTapToFocusEnabled = true
        cameraControl.isPinchToZoomEnabled = true
        //BUTTONS
        //Capture Button
        postView.cameraButton.setOnClickListener {
            val outputFileOptions = ImageCapture.OutputFileOptions.Builder(File("${context?.externalCacheDir}/IMG-${System.currentTimeMillis()}.JPEG")).build()
            cameraControl.takePicture(outputFileOptions,ContextCompat.getMainExecutor(postView.context)
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

        //Toggle Camera

        postView.toggle_camera.setOnClickListener {
            toggleCamera(cameraControl)
        }


    }


    override fun onDown(p0: MotionEvent?): Boolean {
        return true
    }

    override fun onShowPress(p0: MotionEvent?) {

    }

    override fun onSingleTapUp(p0: MotionEvent?): Boolean {
       return false
    }

    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
       return false
    }

    override fun onLongPress(p0: MotionEvent?) {

    }

    override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {

        if (p0 != null && p1 != null) {
                if(abs(p0.x - p1.x) > SWIPE_MAX_OFF_PATH)
                {
                    return false
                }
            if(p1.y > p0.y)
            {
                if(p1.y - p0.y > SWIPE_MIN_DISTANCE && abs(p3) > SWIPE_THRESHOLD_VELOCITY)
                {
                    bottomSheetFragment.show(childFragmentManager, "BottomSheetFragment")
                    return true
                }
            }
        }
        return false
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