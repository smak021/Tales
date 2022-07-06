package com.example.tale.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tale.R
import com.example.tale.model.StoryDetails
import kotlinx.android.synthetic.main.tales_view.view.*

class UserActivityTaleAdapter(private val context: Context?, list: ArrayList<StoryDetails>) : RecyclerView.Adapter<UserActivityTaleAdapter.ViewHolder>() {

    private var tales= ArrayList<StoryDetails>()
    init {
        tales=list
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tales_view,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView).load(tales[position].geturl())
            .placeholder(R.drawable.image_placeholder)
            .into(holder.itemView.talesImage)
    }

    override fun getItemCount(): Int {
        return  tales.size
    }

    class ViewHolder(ItemView: View):RecyclerView.ViewHolder(ItemView) {

    }
}