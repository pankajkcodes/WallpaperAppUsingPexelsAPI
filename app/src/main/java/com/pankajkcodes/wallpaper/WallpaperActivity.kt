package com.pankajkcodes.wallpaper

import androidx.appcompat.app.AppCompatActivity
import android.app.WallpaperManager
import android.os.Bundle
import com.pankajkcodes.wallpaper.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import android.graphics.drawable.Drawable
import com.bumptech.glide.load.engine.GlideException
import android.graphics.Bitmap
import android.view.View
import android.widget.*
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.request.target.Target
import java.io.IOException

class WallpaperActivity : AppCompatActivity() {
    //on below line we are creating variables on below line for imageview and wallpaper manager
    var wallpaperManager: WallpaperManager? = null
    var image: ImageView? = null
    var url: String? = null
    private var wallpaperRL: RelativeLayout? = null
    private var loadingPB: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallpaper)
        wallpaperRL = findViewById(R.id.idRLWallpaper)
        //initializing all variables on below line.
        url = intent.getStringExtra("imgUrl")
        image = findViewById(R.id.image)
        loadingPB = findViewById(R.id.idPBLoading)
        //calling glide to load image from url on below line.
        Glide.with(this).load(url).listener(object : RequestListener<Drawable?> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any,
                target: Target<Drawable?>,
                isFirstResource: Boolean
            ): Boolean {
                //making progress bar visibility to gone on below line.
                loadingPB?.setVisibility(View.GONE)
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any,
                target: Target<Drawable?>,
                dataSource: DataSource,
                isFirstResource: Boolean
            ): Boolean {
                //making progress bar visibility to gone on below line when image is ready.
                loadingPB?.setVisibility(View.GONE)
                return false
            }
        }).into(image!!)
        wallpaperManager = WallpaperManager.getInstance(applicationContext)
        val setWallpaper = findViewById<Button>(R.id.idBtnSetWallpaper)

        //on below line we are adding on click listner to our set wallpaper button.
        setWallpaper.setOnClickListener {
            Glide.with(this@WallpaperActivity)
                .asBitmap().load(url)
                .listener(object : RequestListener<Bitmap?> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        o: Any,
                        target: Target<Bitmap?>,
                        b: Boolean
                    ): Boolean {
                        Toast.makeText(
                            this@WallpaperActivity,
                            "Fail to load image..",
                            Toast.LENGTH_SHORT
                        ).show()
                        return false
                    }

                    override fun onResourceReady(
                        bitmap: Bitmap?,
                        o: Any,
                        target: Target<Bitmap?>,
                        dataSource: DataSource,
                        b: Boolean
                    ): Boolean {
                        //on below line we are setting wallpaper using wallpaper manager on below line.
                        try {
                            wallpaperManager?.setBitmap(bitmap)
                        } catch (e: IOException) {
                            //on below line we are handling exception.
                            Toast.makeText(
                                this@WallpaperActivity,
                                "Fail to set wallpaper",
                                Toast.LENGTH_SHORT
                            ).show()
                            e.printStackTrace()
                        }
                        return false
                    }
                }
                ).submit()
            //displaying custom toast on below line.
//                new StyleableToast
//                        .Builder(WallpaperActivity.this)
//                        .text("Wallpaper Set to Home Screen")
//                        .textColor(Color.WHITE)
//                        .backgroundColor(getResources().getColor(R.color.black_shade_1))
//                        .show();
//                FancyToast.makeText(WallpaperActivity.this, "Wallpaper Set to Home Screen", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
        }
    }
}