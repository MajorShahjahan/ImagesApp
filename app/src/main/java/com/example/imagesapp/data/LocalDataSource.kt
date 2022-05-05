package com.example.imagesapp.data

import com.example.imagesapp.data.database.ImagesDao
import com.example.imagesapp.data.database.ImagesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val imagesDao: ImagesDao){

    suspend fun insertImages(imagesEntity: ImagesEntity){
        imagesDao.insertImages(imagesEntity)
    }

     fun readDatabase() : Flow<List<ImagesEntity>>{
        return imagesDao.readImages()
    }
}