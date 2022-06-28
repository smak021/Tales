package com.example.tale.misc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.tale.R
import com.example.tale.model.followersL
import kotlinx.android.synthetic.main.activity_user_profile.*

class UserProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        val uid: String = intent.getSerializableExtra("uid") as String
        println(uid)
        textView.text = uid
        if(followersL.value!=null) {
            if (followersL.value!!.contains(uid)) {
                followButton.visibility = View.GONE
            }
            else
            {
                followButton.visibility = View.VISIBLE
            }
        }
    }
}