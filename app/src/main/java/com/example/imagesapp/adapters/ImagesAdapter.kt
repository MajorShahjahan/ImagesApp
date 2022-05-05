package com.example.imagesapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesapp.databinding.ImagesRowLayoutBinding
import com.example.imagesapp.models.Hit
import com.example.imagesapp.models.Images
import com.example.imagesapp.util.ImagesDiffUtil

class ImagesAdapter : RecyclerView.Adapter<ImagesAdapter.MyViewHolder>() {

    private var image = emptyList<Hit>()

    class MyViewHolder(private val binding: ImagesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(hit: Hit){
                binding.hit = hit
                binding.executePendingBindings()
            }
        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ImagesRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentHit = image[position]
        holder.bind(currentHit)
    }

    override fun getItemCount(): Int {
        return image.size
    }

    fun setData(newData : Images){
        val imagesDiffUtil = ImagesDiffUtil(image,newData.hits)
        val diffUtilResult = DiffUtil.calculateDiff(imagesDiffUtil)
        image = newData.hits
        diffUtilResult.dispatchUpdatesTo(this)

    }
}