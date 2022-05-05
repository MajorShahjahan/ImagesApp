package com.example.imagesapp.data

import com.example.imagesapp.models.Images
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val imagesApi: ImagesApi) {

    suspend fun getImages(queries : Map<String,String>): Response<Images>{
        return imagesApi.getImages(queries)
    }

    suspend fun searchImages(searchQueries : Map<String,String>): Response<Images>{
        return imagesApi.searchImages(searchQueries)
    }
}