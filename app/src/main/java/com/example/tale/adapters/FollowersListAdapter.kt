package com.example.tale.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.example.tale.R
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tale.model.FollowersDetails
import kotlinx.android.synthetic.main.followers_layout.view.*

class FollowersListAdapter(private val context: Context?, list: MutableList<FollowersDetails>?) : RecyclerView.Adapter<FollowersListAdapter.ViewHolder>(){
    private var followersListt = mutableListOf<FollowersDetails>()
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
        holder.itemView.followers_name.text = followersListt[position].getfollowerName().toString()
        holder.itemView.follower_mail.text = followersListt[position].getfollowerEmail().toString()
    }

    override fun getItemCount(): Int {
        return followersListt.size
    }



    class ViewHolder(ItemView: View):RecyclerView.ViewHolder(ItemView) {

    }
}