package com.example.tale.misc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tale.MainActivity
import com.example.tale.R
import com.example.tale.adapters.FollowersListAdapter
import com.example.tale.model.FetchIndividual
import com.example.tale.model.FollowersDetails
import com.example.tale.model.followersList
import com.example.tale.model.uid
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


        CoroutineScope(IO).launch {
        val followerss = CoroutineScope(IO).async {

            val followers = fetchFollowers()
            //list = followers
            followers
        }
        list = followerss.await()
            withContext(Main)
            {
                println(list)
                delay(500)
                adapter = FollowersListAdapter(baseContext ,list)
                recyclerView.adapter = adapter
            }
        }
    }

    suspend fun fetchFollowers(): MutableList<FollowersDetails>? {
        val followers = FetchIndividual.fetchFollowers(uid!!)
        return followers
    }

}