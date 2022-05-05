package com.example.imagesapp.bindingadapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import coil.load
import com.example.imagesapp.R
import com.example.imagesapp.models.Hit
import com.example.imagesapp.ui.DetailsActivity


class ImagesRowBinding(context: Context) {

    companion object{


        @SuppressLint("StaticFieldLeak")
        private lateinit var context: Context

        fun setContext(con: Context) {
            context=con
        }

        @BindingAdapter("imagesOnClickListener")
        @JvmStatic
        fun imagesOnclickListener(imagesRowLayout : ConstraintLayout, hit : Hit){
            imagesRowLayout.setOnClickListener {

                context.startActivity(Intent(context, DetailsActivity::class.java).apply {
                    putExtra("data", hit)

                })

            }

        }

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, imageUrl : String){
            imageView.load(imageUrl){
                crossfade(600)
                error(R.drawable.ic_error_placeholder)
            }
        }

        @BindingAdapter("setNumberOfLikes")
        @JvmStatic
        fun setNumberOfLikes(textView: TextView, likes : Int){
            textView.text = likes.toString()
        }

        @BindingAdapter("setNumberOfComments")
        @JvmStatic
        fun setNumberOfComments(textView: TextView, comments : Int){
            textView.text = comments.toString()
        }


    }
}