package com.example.tale.misc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tale.R
import com.example.tale.adapters.ProfileTaleAdapter
import com.example.tale.adapters.UserActivityTaleAdapter
import com.example.tale.model.followersL
import com.example.tale.viewModel.UserActivityViewModel
import kotlinx.android.synthetic.main.activity_user_profile.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

class UserProfileActivity : AppCompatActivity() {
    lateinit var mDetector: GestureDetectorCompat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        val uid: String = intent.getSerializableExtra("uid") as String
        val recycler = findViewById<RecyclerView>(R.id.upTalesRecyclerview)
        recycler.layoutManager = GridLayoutManager(this,3)
        //upTextView.text = uid
        if(followersL.value!=null) {
            if (followersL.value!!.contains(uid)) {
                upFollowButton.visibility = View.GONE
            }
            else
            {
                upFollowButton.visibility = View.VISIBLE
            }
        }

        val viewModel = ViewModelProvider(this)[UserActivityViewModel::class.java]
        viewModel.fetchUserData(uid)
        viewModel.userData.observe(this){
            if (it.isNotEmpty()) {
                upTextView.text = it[0].getfull_name().toString()
                if(it[0].getuserPic()!=null){
                    Glide.with(this).load(it[0].getuserPic()).into(upProfilePic)}
            }
        }
        viewModel.fetchFollowersList(uid)
        viewModel.followersLiveData.observe(this){
            if(it.isNotEmpty())
            {
                upFollowers_count.text = it.size.toString()

            }else
            {
                upFollowers_count.text = "0"
            }

        }
        viewModel.fetchUserStory(uid)
        viewModel.storyData.observe(this){
            upStory_count.text = it.size.toString()
            recycler.adapter= UserActivityTaleAdapter(this,it)
            if(it.isEmpty())
            {
                recycler.visibility = View.GONE
                upNoresultLT.visibility = View.VISIBLE
                upInfo.visibility = View.VISIBLE
            }
            else
            {
                recycler.visibility = View.VISIBLE
                upNoresultLT.visibility = View.GONE
                upInfo.visibility = View.GONE
            }
        }
        viewModel.fetchFollowingList(uid)
        viewModel.followingLiveData.observe(this) {
            println(it.size.toString())
            if(it.isNotEmpty()) {
                upFollowing_count.text = it.size.toString()
            }
            else{
                upFollowing_count.text = "0"
            }
        }
    }


}