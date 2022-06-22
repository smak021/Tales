package com.example.tale.misc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tale.MainActivity
import com.example.tale.R
import com.example.tale.adapters.FollowersListAdapter
import com.example.tale.model.FetchIndividual
import com.example.tale.model.FollowersDetails
import com.example.tale.model.followersList
import com.example.tale.model.uid
import com.example.tale.viewModel.ProfileFragmentViewModel
import kotlinx.android.synthetic.main.activity_follower.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class FollowerActivity : AppCompatActivity() {
    private lateinit var adapter: FollowersListAdapter
    private var list:MutableList<FollowersDetails>?= mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_follower)

        val recyclerView = findViewById<RecyclerView>(R.id.followersRecycler)
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        val viewModel = ViewModelProvider(this)[ProfileFragmentViewModel::class.java]
        viewModel.fetchFollowersList()
        viewModel.followers.observe(this, Observer {
            adapter = FollowersListAdapter(baseContext ,it)
            recyclerView.adapter = adapter
        })
    }
}