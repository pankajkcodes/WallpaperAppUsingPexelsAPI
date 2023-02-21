package com.pankajkcodes.wallpaper

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import com.pankajkcodes.wallpaper.R
import com.bumptech.glide.Glide
import android.content.Intent
import android.view.View
import android.widget.ImageView
import com.pankajkcodes.wallpaper.WallpaperActivity
import androidx.cardview.widget.CardView
import java.util.ArrayList

class WallpaperRVAdapter     //creating a constructor.
    (//creating variables for array list and context.
    private val wallPaperList: ArrayList<String>, private val context: Context
) : RecyclerView.Adapter<WallpaperRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //inflating our layout file on below line.
        val view = LayoutInflater.from(context).inflate(R.layout.wallpaper_rv_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //setting data to all our views.
        Glide.with(context).load(wallPaperList[position]).into(holder.wallpaperIV)
        //adding on click listener to item view.
        holder.itemView.setOnClickListener { //passing data through intent on below line.
            val i = Intent(context, WallpaperActivity::class.java)
            i.putExtra("imgUrl", wallPaperList[position])
            context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return wallPaperList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //creating variables for our views which are created in layout file.
        private val imageCV: CardView
        val wallpaperIV: ImageView

        init {
            //initializing all the variables.
            wallpaperIV = itemView.findViewById(R.id.idIVWallpaper)
            imageCV = itemView.findViewById(R.id.idCVWallpaper)
        }
    }
}