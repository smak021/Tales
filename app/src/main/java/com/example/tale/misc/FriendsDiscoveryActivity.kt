package com.example.tale.misc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tale.R
import com.example.tale.adapters.FriendsAdapter
import com.example.tale.viewModel.FriendsDiscoveryViewModel
import kotlinx.android.synthetic.main.activity_friends_discovery.*

class FriendsDiscoveryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends_discovery)

        friendsRecycler.layoutManager = GridLayoutManager(this,3,GridLayoutManager.VERTICAL,false)

        val viewModel = ViewModelProvider(this)[FriendsDiscoveryViewModel::class.java]
        viewModel.getData()
        viewModel.userData.observe(this) {
            friendsRecycler.adapter = FriendsAdapter(it)
        }
    }
}