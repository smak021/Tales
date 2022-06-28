package com.example.tale

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.viewpager2.widget.ViewPager2
import com.example.tale.adapters.TabPageAdapter
import com.example.tale.auth.LoginActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.TAB_LABEL_VISIBILITY_UNLABELED
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

var isFirst: Boolean = true
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val user = FirebaseAuth.getInstance().currentUser
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        isFirst = sharedPref.getBoolean("isFirst", true)
        skipDP.setOnClickListener {
            val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return@setOnClickListener
            with (sharedPref.edit()) {
                putBoolean("isFirst", false)
                welcomeLayout.visibility=View.GONE
                apply()
            }
        }
        if (user == null)
        {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
        if(isFirst)
        {
            welcomeLayout.visibility= View.VISIBLE
        }
        else
        {
            welcomeLayout.visibility=View.GONE
        }
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

    override fun onDestroy() {
        super.onDestroy()
        println("Main Activity Destroyed")
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






