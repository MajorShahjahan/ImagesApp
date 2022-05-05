package com.example.imagesapp.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.example.imagesapp.util.Constants.Companion.ACTUAL_QUERY
import com.example.imagesapp.util.Constants.Companion.API_KEY
import com.example.imagesapp.util.Constants.Companion.QUERY_API_KEY
import com.example.imagesapp.util.Constants.Companion.QUERY_IMAGE_TYPE

class ImagesViewModel(application: Application) : AndroidViewModel(application) {

    var networkStatus = false


    fun applyQueries(): HashMap<String,String>{
        val queries : HashMap<String,String> = HashMap()
        queries[QUERY_API_KEY] = API_KEY
        queries[ACTUAL_QUERY] = "fruits"
        queries[QUERY_IMAGE_TYPE] = "photo"
        return queries
    }

    fun applySearchQueries(searchQuery : String): HashMap<String,String>{
        val queries : HashMap<String,String> = HashMap()
        queries[QUERY_API_KEY] = API_KEY
        queries[ACTUAL_QUERY] = searchQuery
        queries[QUERY_IMAGE_TYPE] = "photo"
        return queries

    }

    fun showNetworkStatus(){
        if (!networkStatus){
            Toast.makeText(getApplication(),"No Internet Connection",Toast.LENGTH_SHORT).show()
        }
    }
}