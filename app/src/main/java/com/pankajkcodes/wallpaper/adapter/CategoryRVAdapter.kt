package com.pankajkcodes.wallpaper.adapter

import com.pankajkcodes.wallpaper.model.CategoryRVModal
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import android.widget.TextView
import com.pankajkcodes.wallpaper.R
import java.util.ArrayList

class CategoryRVAdapter     //creating a constructor.
    (//creating variables for array list and context and interface.
    private val categoryRVModals: ArrayList<CategoryRVModal>,
    private val context: Context,
    private val categoryClickInterface: CategoryClickInterface
) : RecyclerView.Adapter<CategoryRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //inflating our layout file on below line.
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.category_rv_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        //setting data to all our views.
        val modal = categoryRVModals[position]
        holder.categoryTV.text = modal.category
        if (!modal.imgUrl.isEmpty()) {
            Glide.with(context).load(modal.imgUrl).into(holder.categoryIV)
        } else {
            holder.categoryIV.setImageResource(R.drawable.ic_launcher_background)
        }
        //adding on click listner to item view on below line.
        holder.itemView.setOnClickListener { //passing position with interface.
            categoryClickInterface.onCategoryClick(position)
        }
    }

    override fun getItemCount(): Int {
        return categoryRVModals.size
    }

    //creating an interface on below line.
    interface CategoryClickInterface {
        fun onCategoryClick(position: Int)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //creating variables on below line.
        val categoryTV: TextView
        val categoryIV: ImageView

        init {
            //initializing all variables on below line.
            categoryIV = itemView.findViewById(R.id.idIVCategory)
            categoryTV = itemView.findViewById(R.id.idTVCategory)
        }
    }
}