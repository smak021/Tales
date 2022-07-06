package com.example.tale.misc


import android.os.Bundle
import android.transition.Transition
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.example.tale.R
import com.example.tale.model.StoryDetails
import com.example.tale.model.UserDetails
import com.example.tale.viewModel.StoryViewModel
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_story.*

class StoryActivity : AppCompatActivity() {
    private var i = 0

    private var progressList: ArrayList<com.google.android.material.progressindicator.LinearProgressIndicator> =
        ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)
        i = 0
        val url: ArrayList<StoryDetails> =
            intent.getSerializableExtra("url") as ArrayList<StoryDetails>
        val name: UserDetails = intent.getSerializableExtra("name") as UserDetails
        //val pos: Int = intent.getSerializableExtra("position") as Int
        val numberOfSection = url.size
        for (i in 0..numberOfSection) {
            val progressBar =
                com.google.android.material.progressindicator.LinearProgressIndicator(this)
            progressLayout.weightSum = numberOfSection.toFloat()
            val params = LinearLayout.LayoutParams(0, 10, 1F)
            params.setMargins(10, 0, 0, 0)
            progressBar.isIndeterminate = false
            progressBar.layoutParams = params
            //progressBar.trackColor = R.color.track
            // progressBar.setIndicatorColor(R.color.white)
            progressBar.trackCornerRadius = 20
            progressBar.max = 1000
            progressList.add(progressBar)
            progressLayout.addView(progressBar)
        }
        val viewModel = ViewModelProvider(this)[StoryViewModel::class.java]
        //viewModel.getUserData(name.getemail()!!)


        viewModel.position.observe(this) {
            storyTime.text = url[it].gettime()?.toDate()?.toString()
            //Glide.with(this).load(url[it].).into(profPic)
            Glide.with(this).load(name.getuserPic()).into(profPic)
            storyName.text = name.getfull_name()
            println(it)
            println(url.size)
            progressList[it].progress = 500
            //ProgressBar handler Start
            /*var count =0
            timer = object: CountDownTimer(150000, 15) {
                override fun onTick(millisUntilFinished: Long) {
                   progressList[it].progress = count++
                }
                override fun onFinish() {
                    println(it)
                    println(url.size)
                    if(it == url.size-1)
                    {
                        finishAfterTransition()
                    }
                    else{
                        viewModel.incrementData()
                    }

                }
            }
            timer.start()

            */
            //progressBar handler End
            Glide.with(this).load(url[it].geturl())
                .transition(withCrossFade())
                .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 5)))
                .apply(RequestOptions().override(150, 150)).into(background_blur)
            Glide.with(this).load(url[it].geturl())
                .transition(withCrossFade())
                .into(story_viewer)
        }

        story_viewer.keepScreenOn = true
        story_viewer.setOnTouchListener { p0, p1 ->
            val halfWidth = p0?.width!! / 2
            when (p1?.action) {
                MotionEvent.ACTION_DOWN -> {
                    Log.d("DEBUG_TAG", "Action was DOWN")

                }
                MotionEvent.ACTION_UP -> {
                    if ((halfWidth <= p1.x) && ((url.size - 1) > viewModel.position.value!!)) {
                        viewModel.incrementData()
                    } else
                        if ((halfWidth > p1.x) && (viewModel.position.value != 0)) {
                            viewModel.decrementData()
                        } else {
                            viewModel.resetData()
                            textContainerLayout.visibility = View.GONE
                            finishAfterTransition()
                        }
                    p0.performClick()

                }

            }
            p0.onTouchEvent(p1)
            true
        }


        /*
            var count =0
            val timer = object: CountDownTimer(30000, 30) {
                override fun onTick(millisUntilFinished: Long) {
                    linearProgressIndicator.progress = count++
                }

                override fun onFinish() {
                    finishAfterTransition()
                }
            }
            timer.start()

         */




        if (addTransitionListener()) {
            //textContainerLayout.visibility = View.GONE
        } else {
            textContainerLayout.visibility = View.VISIBLE
        }

    }


    override fun onBackPressed() {
        super.onBackPressed()
        textContainerLayout.visibility = View.GONE
    }

    @RequiresApi(21)
    private fun addTransitionListener(): Boolean {
        val transition: Transition? = window.sharedElementEnterTransition
        if (transition != null) {
            // There is an entering shared element transition so add a listener to it
            transition.addListener(object : Transition.TransitionListener {
                override fun onTransitionEnd(transition: Transition) {
                    // As the transition has ended, we can now load the full-size image

                    //textContainerLayout.visibility = View.VISIBLE
                    // Make sure we remove ourselves as a listener
                    transition.removeListener(this)
                }

                override fun onTransitionStart(transition: Transition?) {
                    // textContainerLayout.visibility = View.GONE
                    // No-op
                }

                override fun onTransitionCancel(transition: Transition) {
                    // Make sure we remove ourselves as a listener
                    transition.removeListener(this)
                }

                override fun onTransitionPause(transition: Transition?) {
                    // No-op
                }

                override fun onTransitionResume(transition: Transition?) {
                    // No-op
                }
            })
            return true
        }

        // If we reach here then we have not added a listener
        return false
    }

}