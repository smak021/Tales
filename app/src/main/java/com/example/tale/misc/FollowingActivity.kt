package com.example.tale.misc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tale.R
import com.example.tale.adapters.FollowersListAdapter
import com.example.tale.model.*
import com.example.tale.viewModel.FollowersViewModel

class FollowingActivity : AppCompatActivity() {
    private lateinit var adapter: FollowersListAdapter
    private var list:MutableList<FollowersDetails>?= mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_following)

        val recyclerView = findViewById<RecyclerView>(R.id.followersRecycler)
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        val viewModel = ViewModelProvider(this)[FollowersViewModel::class.java]
        viewModel.fetchFriends()
       viewModel.friends.observe(this) {
           adapter = FollowersListAdapter(baseContext, it)
           recyclerView.adapter = adapter
       }



    }
}