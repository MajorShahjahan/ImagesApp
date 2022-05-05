package com.example.imagesapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.navArgument
import coil.load
import com.example.imagesapp.R
import com.example.imagesapp.models.Hit
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.placeholder_row.*

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val hit = intent.getParcelableExtra<Hit>("data")
        main_imageView.load(hit?.largeImageURL)
        likes_textView.text = hit?.likes.toString()
        comment_textView.text = hit?.comments.toString()
        downloads_textView.text = hit?.downloads.toString()
        user_textView.text = hit?.user
        imageTag_textView.text = hit?.tags


    }
}