package com.example.tale.misc


import android.os.Bundle
import android.os.CountDownTimer
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.example.tale.R
import com.example.tale.model.StoryDetails
import com.example.tale.viewModel.StoryViewModel
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_story.*

class StoryActivity : AppCompatActivity(){
    private var i = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)
        i = 0
        val url:ArrayList<StoryDetails> = intent.getSerializableExtra("url") as ArrayList<StoryDetails>
        val name: String = intent.getSerializableExtra("name") as String
        val pos:Int = intent.getSerializableExtra("position") as Int

        val viewModel = ViewModelProvider(this)[StoryViewModel::class.java]
        viewModel.position.observe(this) {
            storyTime.text = url[it].gettime()?.toDate()?.toString()
            storyName.text = name

            Glide.with(this).load(url[it].geturl())
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
                    if ((halfWidth <= p1.x) && ((url.size - 1) > viewModel.position.value!!)) {
                        viewModel.incrementData()
                    } else
                        if ((halfWidth > p1.x) && (viewModel.position.value != 0)) {
                        viewModel.decrementData()
                        } else {
                            viewModel.resetData()
                            finish()
                        }
                    p0.performClick()
                }

            }
            p0.onTouchEvent(p1)
        }
        var count =0
        val timer = object: CountDownTimer(30000, 30) {
            override fun onTick(millisUntilFinished: Long) {
                linearProgressIndicator.progress = count++
            }

            override fun onFinish() {
                finish()
            }
        }
        timer.start()

    }

}