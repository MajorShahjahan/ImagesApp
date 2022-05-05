package com.example.imagesapp.data.database


import androidx.room.TypeConverter
import com.example.imagesapp.models.Images
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class ImagesTypeConvertor {

    var gson = Gson()

    @TypeConverter
    fun imagesToString(images: Images): String {
        return gson.toJson(images)
    }

    @TypeConverter
    fun stringToImages(data: String): Images {
        val listType = object : TypeToken<Images>() {}.type
        return gson.fromJson(data, listType)
    }
}