package com.example.tale


import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.LinearLayout
import android.widget.SeekBar
import android.graphics.Color
import androidx.constraintlayout.helper.widget.Carousel
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tale.adapters.TestAdapter
import kotlinx.android.synthetic.main.activity_collapse_test2.*

class CollapseTestActivity2 : AppCompatActivity() {
    var colors = intArrayOf(
        Color.parseColor("#ffd54f"),
        Color.parseColor("#ffca28"),
        Color.parseColor("#ffc107"),
        Color.parseColor("#ffb300"),
        Color.parseColor("#ffa000"),
        Color.parseColor("#ff8f00"),
        Color.parseColor("#ff6f00"),
        Color.parseColor("#c43e00")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collapse_test2)
        carousel.setAdapter(object : Carousel.Adapter{
            override fun count(): Int {
                return colors.size
            }

            override fun populate(view: View?, index: Int) {
                view?.setBackgroundColor(colors[index])
            }

            override fun onNewItem(index: Int) {

            }

        })
        //val recycler = findViewById<RecyclerView>(R.id.rvToDoList)
        //recycler.layoutManager = GridLayoutManager(this,3)
        //recycler.adapter=TestAdapter()

    }
}