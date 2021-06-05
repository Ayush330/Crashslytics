package com.example.crashslytics

import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class Adapter(private val data:List<MovieData>) : RecyclerView.Adapter<Adapter.ViewHolder>()
{
    class ViewHolder(view: View):RecyclerView.ViewHolder(view)
    {
        val title = view.findViewById<TextView>(R.id.title)
        val image = view.findViewById<ImageView>(R.id.image)
        val desc = view.findViewById<TextView>(R.id.description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_layout, parent, false)

        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        /**
         * data
         */
        Glide.with(holder.itemView).load(data[position].imageUrl).centerCrop()
            .into(holder.image)
        holder.title.text = data[position].name
        holder.title.paint.isUnderlineText=true
        holder.title.paint.underlineColor = Color.BLUE
        holder.desc.text = data[position].desc
    }

    override fun getItemCount(): Int {
        return data.size
    }
}