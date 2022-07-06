package com.example.tale.adapters

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.example.tale.R
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tale.misc.UserProfileActivity
import com.example.tale.model.FollowersDetails
import com.example.tale.model.Tale
import com.example.tale.model.UserDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.followers_layout.view.*


class FollowersListAdapter(private val context: Context?, list: ArrayList<UserDetails>) : RecyclerView.Adapter<FollowersListAdapter.ViewHolder>(){
    private var followersListt = ArrayList<UserDetails>()
    init {
        followersListt= list
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.followers_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (followersListt[position].getuserPic()!=null) {
            Glide.with(holder.itemView.context).load(followersListt[position].getuserPic())
                .into(holder.dpImage)
        }
        holder.followerName.text = followersListt[position].getfull_name().toString()
        holder.followerMail.text = followersListt[position].getemail().toString()
        holder.boxLayout.setOnClickListener {
            holder.itemView.context.startActivity(Intent(context,UserProfileActivity::class.java)
                .putExtra("uid",followersListt[position].getemail()))
        }
        holder.removeButton.setOnClickListener {

            val emailToDel = followersListt[position].getemail()
            if (emailToDel != null)
                Tale.unfollow(emailToDel)
        }


    }

    override fun getItemCount(): Int {
        return followersListt.size
    }



    class ViewHolder(ItemView: View):RecyclerView.ViewHolder(ItemView) {
        val dpImage: ImageView = ItemView.findViewById(R.id.follower_dp)
        val followerName:TextView = ItemView.findViewById(R.id.followers_name)
        val followerMail:TextView = ItemView.findViewById(R.id.follower_mail)
        val boxLayout:LinearLayout = ItemView.findViewById(R.id.followerBoxLayout)
        val removeButton:Button = ItemView.findViewById(R.id.removePerson)
    }
}