package com.example.tale

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.viewpager2.widget.ViewPager2
import com.example.tale.adapters.TabPageAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.TAB_LABEL_VISIBILITY_UNLABELED
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // supportActionBar?.hide()
        setSupportActionBar(toolbar)
        val adapter = TabPageAdapter(this, tabLayout.tabCount)
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //TabLayout & Viewpager connection
    private fun TabLayout.setupWithViewPager(viewPager: ViewPager2) {
        viewPager.setCurrentItem(1,false)
        TabLayoutMediator(
            this, viewPager
        ) { tab, position ->

            when (position) {
                1 -> tab.icon = AppCompatResources.getDrawable(context,R.drawable.home)
                2 -> tab.icon = AppCompatResources.getDrawable(context,R.drawable.profile)
                0 -> {tab.view.visibility= View.GONE
                    tab.tabLabelVisibility = TAB_LABEL_VISIBILITY_UNLABELED

                    }
            }
        }.attach()
    }

}






