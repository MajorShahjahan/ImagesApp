package com.example.imagesapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.imagesapp.models.Images
import com.example.imagesapp.util.Constants.Companion.IMAGES_TABLE

@Entity(tableName = IMAGES_TABLE)
class ImagesEntity(var images: Images) {

    @PrimaryKey(autoGenerate = false)
    var id : Int = 0
}