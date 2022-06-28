package com.example.tale.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tale.R
import com.example.tale.model.UserDetails
import com.google.firebase.firestore.auth.User
import kotlinx.android.synthetic.main.friends_row.view.*
import java.util.zip.Inflater

class FriendsAdapter(arrayList: ArrayList<UserDetails>) : RecyclerView.Adapter<FriendsAdapter.ViewHolder>() {
    var arrayList = ArrayList<UserDetails>()
    init {
        this.arrayList = arrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.friends_row,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.friendsName.text = arrayList[position].getfull_name().toString()
    }

    override fun getItemCount(): Int {
       return arrayList.size
    }

    class ViewHolder(ItemView: View):RecyclerView.ViewHolder(ItemView) {

    }
}