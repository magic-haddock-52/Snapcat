package com.example.snapcat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class CustomAdapter(private val modelList: List<ItemsViewModel>)
    : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ivm = modelList[position]

        Picasso.get()
            .load(ivm.image)
            .into(holder.imageView)

        //holder.imageView.setImageResource(ivm.image)

        holder.textView.text = ivm.text
    }

    override fun getItemCount(): Int {
        return modelList.size
    }



    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        //imageView.setOnClickListener

        val textView: TextView = itemView.findViewById(R.id.textView)
    }
}





