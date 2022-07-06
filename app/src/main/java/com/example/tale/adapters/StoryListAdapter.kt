package com.example.tale.adapters

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tale.misc.StoryActivity
import com.example.tale.R
import com.example.tale.model.StoryDetails
import com.example.tale.model.UserDetails
import kotlinx.android.synthetic.main.circular_image.view.*


class StoryListAdapter(
    private val context: Context?,
    finall: Map<UserDetails, ArrayList<StoryDetails>>,
    spanCount: Int
) : RecyclerView.Adapter<StoryListAdapter.ViewHolder>() {
    private var list = mapOf<UserDetails, ArrayList<StoryDetails>>()
    private var spanCount:Int = 0
    init {
        list = finall
        this.spanCount = spanCount

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.circular_image, parent, false)

        val width = Resources.getSystem().displayMetrics.widthPixels
        val dpi = Resources.getSystem().displayMetrics.densityDpi
        val marginPx = 10 * (dpi / 160) //10
        val remainingPx = (width - (marginPx * (spanCount*2)))
        val cardWidth = remainingPx / spanCount
        println("marginPx:${marginPx} cardWidth:$cardWidth Width:$width DPI:$dpi RemainingPx:$remainingPx")
        val paramss = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
       // paramss.setMargins(marginPx.toInt(), 2, 0, 2)
        view.linearLayoutCard.layoutParams = paramss
        view.storyCard.layoutParams.width = cardWidth.toInt()
        view.storyCard.layoutParams.height = cardWidth.toInt()
        println()
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val keys = ArrayList<UserDetails>(list.keys)
        println(keys[position].getemail())
        val name = keys[position]
        val urls: ArrayList<StoryDetails>? = list[name]
        val stringBuilder = name.getfull_name()?.let { StringBuilder(it) }
        if (urls?.size != 0) {
            val last: StoryDetails? = urls?.get(urls.size - 1)
            Glide.with(holder.itemView).load(last?.geturl())
                .centerCrop()
                .into(holder.imageView)
        }
        //println(holder.storyCard.measuredWidth)
        //println(name.getfull_name()?.length)
        val length = name.getfull_name()?.length
        if (length != null) {
            if (length > 11) {
                holder.nameText.text = "${stringBuilder?.subSequence(0, 11)}..."
            } else
                holder.nameText.text = name.getfull_name()
        }

        // holder.itemView.ownerTextView.text = name
        holder.storyCard.setOnClickListener {
            val myIntent =
                Intent(context, StoryActivity::class.java).putExtra("url", urls)
                    .putExtra("name", name)
                    .putExtra("position", position)
            val options = ActivityOptions.makeSceneTransitionAnimation(
                context as Activity,
                android.util.Pair.create(holder.itemView.cardView, "story")
            )
            holder.itemView.context.startActivity(myIntent, options.toBundle())
        }
    }


    override fun getItemCount(): Int {
        return if (list.size != 0) {
            list.size
        } else {
            0
        }
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.story_thumb)
        val storyCard: CardView = ItemView.findViewById(R.id.storyCard)
       // val cardView: CardView = ItemView.findViewById(R.id.cardView)
        val nameText: TextView = ItemView.findViewById(R.id.ownerTextView)


    }
}

