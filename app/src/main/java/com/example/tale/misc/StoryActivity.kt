package com.example.tale.misc


import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.tale.R
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_story.*

class StoryActivity : AppCompatActivity() {
    private var i = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)
        i = 0
        val url: ArrayList<Any> = intent.getSerializableExtra("url") as ArrayList<Any>
        val name: String = intent.getSerializableExtra("name") as String
        println(name)
        storyName.text = name
        updateGlide(url[i], this)
        story_viewer.keepScreenOn = true
        story_viewer.setOnTouchListener { p0, p1 ->
            val halfWidth = p0?.width!! / 2
            when (p1?.action) {
                MotionEvent.ACTION_DOWN -> {
                    if ((halfWidth <= p1.x) && ((url.size - 1) > i)) {
                        i += 1
                        updateGlide(url[i], story_viewer.context)
                    } else
                        if ((halfWidth > p1.x) && (i != 0)) {
                            i -= 1
                            updateGlide(url[i], story_viewer.context)
                        } else {
                            i = 0
                            finish()
                        }
                    p0.performClick()
                }

            }
            p0.onTouchEvent(p1)
        }
        var count =0
        val timer = object: CountDownTimer(15000, 15) {
            override fun onTick(millisUntilFinished: Long) {
                linearProgressIndicator.progress = count++
            }

            override fun onFinish() {
                finish()
            }
        }
        timer.start()

    }

    private fun updateGlide(url: Any, context: Context) {
        Glide.with(context).load(url)
            .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 5)))
            .apply(RequestOptions().override(150, 150)).into(background_blur)
        Glide.with(context).load(url).into(story_viewer)


    }

}