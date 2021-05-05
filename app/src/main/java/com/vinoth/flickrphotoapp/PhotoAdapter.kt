package com.vinoth.flickrphotoapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vinoth.dataclass.Photo

class PhotoAdapter(private val context: Context, var photos: MutableList<Photo> = mutableListOf()) : RecyclerView.Adapter<PhotoAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.photo_recyclerview, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val photoItem = photos.get(position)
        if (context != null) {
            Glide.with(context)
                .load(photoItem.url)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView)
        }
        holder.textView.text = photoItem.title
    }

    fun addItem(photos: MutableList<Photo>) {
        photos.addAll(photos)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView.findViewById(R.id.textView)
        var imageView: ImageView = itemView.findViewById(R.id.imageView)
    }
}