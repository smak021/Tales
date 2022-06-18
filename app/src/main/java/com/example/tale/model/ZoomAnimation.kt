package com.example.tale.model

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout



class ZoomOutPageTransformer(
    tabLayout: TabLayout,
    toolbar1: androidx.appcompat.widget.Toolbar,
    viewPager: ViewPager2
) : ViewPager2.PageTransformer {
    private var tool:androidx.appcompat.widget.Toolbar
    private lateinit var tab: TabLayout
    private var vp : ViewPager2
    init {
        tool = toolbar1
        tab = tabLayout
        vp = viewPager

    }
    override fun transformPage(view: View, position: Float) {

        view.apply {
            val pageWidth = width
            val pageHeight = height

            when {
                position < -1 -> { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    alpha = 0f
                }
                position <=0 ->{
                    tool.title="h$position"
                }
                position <=1 ->{

                        tool.title= view.width.toString()
                        translationX=position
                        tool.translationY = tool.height * -position
                    tab.translationY= tab.height * +position
                   // vp.scaleY = 1 + ((position /10 *2) - 0.01).toFloat()
                        //(1+ ((position)*2)/10).toFloat()


                   // scaleX = 1.0f+(position/10)
                   // scaleY =  1.0f+(position/10)

                }



                else -> { // (1,+Infinity]
                    // This page is way off-screen to the right.

                    alpha = 0f
                }
            }
        }
    }
}