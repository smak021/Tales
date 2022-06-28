package com.example.tale.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import com.example.tale.R
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tale.misc.UserProfileActivity
import com.example.tale.model.FollowersDetails
import com.example.tale.model.UserDetails
import kotlinx.android.synthetic.main.followers_layout.view.*

class FollowersListAdapter(private val context: Context?, list: ArrayList<UserDetails>) : RecyclerView.Adapter<FollowersListAdapter.ViewHolder>(){
    private var followersListt = ArrayList<UserDetails>()
    init {
        if (list != null) {
            followersListt= list
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.followers_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.followers_name.text = followersListt[position].getfull_name().toString()
        holder.itemView.follower_mail.text = followersListt[position].getemail().toString()
        holder.itemView.followerBoxLayout.setOnClickListener {
            holder.itemView.context.startActivity(Intent(context,UserProfileActivity::class.java)
                .putExtra("uid",followersListt[position].getemail()))
        }
    }

    override fun getItemCount(): Int {
        return followersListt.size
    }



    class ViewHolder(ItemView: View):RecyclerView.ViewHolder(ItemView) {

    }
}