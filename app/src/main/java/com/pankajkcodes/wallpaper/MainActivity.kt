package com.pankajkcodes.wallpaper

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    fun newIntent(view: View?) {
        val i = Intent(this@MainActivity, HomeScreenActivity::class.java)
        startActivity(i)
    }

    private fun getVideoList() {
        val uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val projection =
            arrayOf(MediaStore.Video.VideoColumns.DATA, MediaStore.Video.VideoColumns.DURATION)
        val cursor = contentResolver.query(uri, projection, null, null, null)
        val pathArrList = ArrayList<String>()
        if (cursor != null) {
            while (cursor.moveToNext()) {
                pathArrList.add(cursor.getString(0))
                Log.e(
                    "TAG", """
     VIDEO LINK IS ${cursor.getString(0)}
     ${cursor.getString(1)}
     """.trimIndent()
                )
            }
            cursor.close()
        }
//        Log.e("all path",pathArrList.toString());
    }
}