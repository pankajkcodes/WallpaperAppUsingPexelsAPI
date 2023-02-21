package com.pankajkcodes.wallpaper

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.pankajkcodes.wallpaper.CategoryRVAdapter.CategoryClickInterface
import org.json.JSONException

class HomeScreenActivity : AppCompatActivity(), CategoryClickInterface {

    //creating variables for recyclerview,progress bar,
    // adapter and array list, edittext and others.

    private var categoryRV: RecyclerView? = null
    private var wallpaperRV: RecyclerView? = null
    private var loadingPB: ProgressBar? = null
    private var categoryRVModals: ArrayList<CategoryRVModal>? = null
    private var wallpaperArrayList: ArrayList<String>? = null
    private var categoryRVAdapter: CategoryRVAdapter? = null
    private var wallpaperRVAdapter: WallpaperRVAdapter? = null
    private var searchEdt: EditText? = null
    private var searchIV: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)


        //initializing all variables on below line.
        categoryRV = findViewById(R.id.idRVCategories)
        wallpaperRV = findViewById(R.id.idRVWallpapers)
        searchEdt = findViewById(R.id.idEdtSearch)
        searchIV = findViewById(R.id.idIVSearch)
        loadingPB = findViewById(R.id.idPBLoading)
        wallpaperArrayList = ArrayList()
        categoryRVModals = ArrayList()


        //creating a layout manager for recycler view which is our category.
        val manager1 = LinearLayoutManager(this@HomeScreenActivity, RecyclerView.HORIZONTAL, false)
        //initializing our adapter class on below line.
        wallpaperRVAdapter = WallpaperRVAdapter(wallpaperArrayList!!, this)
        categoryRVAdapter = CategoryRVAdapter(categoryRVModals!!, this, this)
        //setting layout manager to our category recycler view as horizontal.
        categoryRV!!.setLayoutManager(manager1)
        categoryRV!!.setAdapter(categoryRVAdapter)
        //creating a grid layout manager for our wallpaper recycler view.
        val layoutManager = GridLayoutManager(this, 2)
        //setting layout manager and adapter to our recycler view.
        wallpaperRV!!.setLayoutManager(layoutManager)
        wallpaperRV!!.setAdapter(wallpaperRVAdapter)
        //on below line we are calling method to get categories to add data in array list.
        categories
        //on below line we are calling get wallpaper method to get data in wallpaper array list.
        wallpapers
        //on below line we are adding on click listner for search image view on below line.
        searchIV!!.setOnClickListener(View.OnClickListener {
            //inside on click method we are getting data from our search edittext and validating if the input field is empty or not.
            val searchStr = searchEdt!!.getText().toString()
            if (searchStr.isEmpty()) {
                Toast.makeText(
                    this@HomeScreenActivity,
                    "Please enter something to search",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                //on below line we are calling a get wallpaper method to get wallpapers by category.
                getWallpapersByCategory(searchStr)
            }
        })
    }

    //on below line we are creating a method to get the wallpaper by category.
    private fun getWallpapersByCategory(category: String) {
        //on below line we are clearing our array list.
        wallpaperArrayList!!.clear()
        //on below line we are making visibility of our progress bar as gone.
        loadingPB!!.visibility = View.VISIBLE
        //on below line we are creating a string variable for our url and adding url to it.
        val url = "https://api.pexels.com/v1/search?query=$category&per_page=30&page=1"
        //on below line we are creating a new variable for our request queue.
        val queue = Volley.newRequestQueue(this@HomeScreenActivity)
        //on below line we are making a json object request to get the data from url .
        val jsonObjectRequest: JsonObjectRequest =
            object : JsonObjectRequest(Method.GET, url, null, Response.Listener { response ->
                //on below line we are extracting the data from our response and passing it to our array list.
                try {
                    loadingPB!!.visibility = View.GONE
                    //on below line we are extracting json data.
                    val photos = response.getJSONArray("photos")
                    for (i in 0 until photos.length()) {
                        val photoObj = photos.getJSONObject(i)
                        val imgUrl = photoObj.getJSONObject("src").getString("portrait")
                        //on below line we are passing data to our array list
                        wallpaperArrayList!!.add(imgUrl)
                    }
                    //here we are notifying adapter that data has changed in our list.
                    wallpaperRVAdapter!!.notifyDataSetChanged()
                } catch (e: JSONException) {
                    //handling json exception on below line.
                    e.printStackTrace()
                }
            }, Response.ErrorListener { //displaying a simple toast message on error response.
                Toast.makeText(this@HomeScreenActivity, "Fail to get data..", Toast.LENGTH_SHORT)
                    .show()
            }) {
                override fun getHeaders(): Map<String, String> {
                    //in this method passing headers as key along with value as API keys.
                    val headers = HashMap<String, String>()
                    headers["Authorization"] = "Enter API Key"
                    //at last returning headers.
                    return headers
                }
            }
        queue.add(jsonObjectRequest)
    }//in this method passing headers as key along with value as API keys.

    //at last returning headers.
      //displaying a toast message on error respone.
    // handling json exception on below line.
    // on below line we are passing data to our array list
    // on below line we are extracting json data./
    // /on below line we are extracting the data from
    // our response and passing it to our array list.
    //on below line we are clearing our array list.
    //changing visiblity of our progress bar to gone.
    //creating a variable for our url.
    private val wallpapers: Unit
        //on below line we are creating a new variable for our request queue.
        //on below line we are making a json object request to get the data from url .
        private get() {
            //on below line we are clearing our array list.
            wallpaperArrayList!!.clear()
            //changing visiblity of our progress bar to gone.
            loadingPB!!.visibility = View.VISIBLE
            //creating a variable for our url.
            val url = "https://api.pexels.com/v1/curated?per_page=30&page=1"
            //on below line we are creating a new variable for our request queue.
            val queue = Volley.newRequestQueue(this@HomeScreenActivity)
            //on below line we are making a json object request to get the data from url .
            val jsonObjectRequest: JsonObjectRequest =
                object : JsonObjectRequest(Method.GET, url, null, Response.Listener { response ->
                    //on below line we are extracting the data from our response and passing it to our array list.
                    loadingPB!!.visibility = View.GONE
                    try {
                        //on below line we are extracting json data.
                        val photos = response.getJSONArray("photos")
                        for (i in 0 until photos.length()) {
                            val photoObj = photos.getJSONObject(i)
                            val imgUrl = photoObj.getJSONObject("src").getString("portrait")
                            //on below line we are passing data to our array list
                            wallpaperArrayList!!.add(imgUrl)
                        }
                        wallpaperRVAdapter!!.notifyDataSetChanged()
                    } catch (e: JSONException) {
                        //handling json exception on below line.
                        e.printStackTrace()
                    }
                }, Response.ErrorListener { //displaying a toast message on error respone.
                    Toast.makeText(
                        this@HomeScreenActivity,
                        "Fail to get data..",
                        Toast.LENGTH_SHORT
                    ).show()
                }) {
                    override fun getHeaders(): Map<String, String> {
                        //in this method passing headers as key along with value as API keys.
                        val headers = HashMap<String, String>()
                        headers["Authorization"] = "Enter API Key"
                        //at last returning headers.
                        return headers
                    }
                }
            queue.add(jsonObjectRequest)
        }

    //on below lines we are adding data to our category array list.
    private val categories: Unit
        private get() {
            //on below lines we are adding data to our category array list.
            categoryRVModals!!.add(
                CategoryRVModal(
                    "Technology",
                    "https://images.unsplash.com/photo-1526374965328-7f61d4dc18c5?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTJ8fHRlY2hub2xvZ3l8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"
                )
            )
            categoryRVModals!!.add(
                CategoryRVModal(
                    "Programming",
                    "https://images.unsplash.com/photo-1542831371-29b0f74f9713?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8cHJvZ3JhbW1pbmd8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"
                )
            )
            categoryRVModals!!.add(
                CategoryRVModal(
                    "Nature",
                    "https://images.pexels.com/photos/2387873/pexels-photo-2387873.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"
                )
            )
            categoryRVModals!!.add(
                CategoryRVModal(
                    "Travel",
                    "https://images.pexels.com/photos/672358/pexels-photo-672358.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"
                )
            )
            categoryRVModals!!.add(
                CategoryRVModal(
                    "Architecture",
                    "https://images.pexels.com/photos/256150/pexels-photo-256150.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"
                )
            )
            categoryRVModals!!.add(
                CategoryRVModal(
                    "Arts",
                    "https://images.pexels.com/photos/1194420/pexels-photo-1194420.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"
                )
            )
            categoryRVModals!!.add(
                CategoryRVModal(
                    "Music",
                    "https://images.pexels.com/photos/4348093/pexels-photo-4348093.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"
                )
            )
            categoryRVModals!!.add(
                CategoryRVModal(
                    "Abstract",
                    "https://images.pexels.com/photos/2110951/pexels-photo-2110951.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"
                )
            )
            categoryRVModals!!.add(
                CategoryRVModal(
                    "Cars",
                    "https://images.pexels.com/photos/3802510/pexels-photo-3802510.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"
                )
            )
            categoryRVModals!!.add(
                CategoryRVModal(
                    "Flowers",
                    "https://images.pexels.com/photos/1086178/pexels-photo-1086178.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"
                )
            )
        }

    override fun onCategoryClick(position: Int) {
        //on below line we are getting category from our array list and calling a method to get wallpapers by category.
        val category = categoryRVModals!![position].category
        getWallpapersByCategory(category)
    }
}