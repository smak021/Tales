package com.example.tale.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.tale.R
import com.example.tale.misc.GalleryActivity
import com.example.tale.misc.PostPreviewActivity
import com.example.tale.model.UserDetails
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.gallery_image_grid.view.*

class GalleryAdapter(private val context: Context?, list: ArrayList<String>):RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {
    var imagePath = ArrayList<String>()
    init {
        imagePath = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gallery_image_grid,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //Image
        Glide.with(holder.itemView).load(imagePath[position])
            .apply(RequestOptions().override(150, 150))
            .centerCrop().into(holder.itemView.galleryImage)

        holder.itemView.galleryImage.setOnClickListener {
            holder.itemView.context.startActivity(Intent(context,PostPreviewActivity::class.java)
                .putExtra("path",imagePath[position]))
        }
    }

    override fun getItemCount(): Int {
        return imagePath.size
    }


    class ViewHolder(ItemView: View):RecyclerView.ViewHolder(ItemView) {

    }
}