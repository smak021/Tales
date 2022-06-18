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
import kotlinx.android.synthetic.main.circular_image.view.*


class StoryListAdapter(private val context: Context?, finall: LinkedHashMap<String, MutableList<Any>>): RecyclerView.Adapter<StoryListAdapter.ViewHolder>()  {
    private var list = hashMapOf<String, MutableList<Any>>()
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
                val urls = ArrayList<Any>(list.values)
                val urlSet= urls[position] as ArrayList<Any>
                val name = keys[position] as String
                //val urlList = extract(list, position) as ArrayList<Any>
               // holder.itemView.story_name.text = keys[position].toString()
                Glide.with(holder.itemView).load(urlSet[0]).centerCrop()
                    .into(holder.itemView.story_thumb)

                storyCard.setOnClickListener {
                    val myIntent =
                        Intent(context, StoryActivity::class.java).putExtra("url", urlSet).putExtra("name", name)
                    holder.itemView.context.startActivity(myIntent)
                }
    }



    override fun getItemCount(): Int {
       return list.size
    }

    class ViewHolder(ItemView: View):RecyclerView.ViewHolder(ItemView) {

    }
}

