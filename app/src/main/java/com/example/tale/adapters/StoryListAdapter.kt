package com.example.tale.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tale.misc.StoryActivity
import com.example.tale.R
import com.example.tale.model.StoryDetails
import kotlinx.android.synthetic.main.circular_image.view.*


class StoryListAdapter(private val context: Context?, finall: LinkedHashMap<String, ArrayList<StoryDetails>>): RecyclerView.Adapter<StoryListAdapter.ViewHolder>() {
    private var list = hashMapOf<String, ArrayList<StoryDetails>>()
    init {
        list = finall
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.circular_image, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val storyCard =
                holder.itemView.findViewById<LinearLayout>(R.id.storyCard)
              val keys = ArrayList<Any>(list.keys)
                val name = keys[position] as String
                val urls: ArrayList<StoryDetails>? = list[name]
                val last: StoryDetails? = urls?.get(0)
                Glide.with(holder.itemView).load(last?.geturl()).centerCrop()
                    .into(holder.itemView.story_thumb)
                storyCard.setOnClickListener {
                    val myIntent =
                      Intent(context, StoryActivity::class.java).putExtra("url", urls).putExtra("name", name)
                          .putExtra("position",position)
                        holder.itemView.context.startActivity(myIntent)
                }
    }



    override fun getItemCount(): Int {
       return list.size
    }

    class ViewHolder(ItemView: View):RecyclerView.ViewHolder(ItemView) {

    }
}

