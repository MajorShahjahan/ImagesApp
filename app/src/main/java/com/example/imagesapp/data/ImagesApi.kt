package com.example.imagesapp.data

import com.example.imagesapp.models.Images
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ImagesApi {

    @GET("/api/?")
    suspend fun getImages(
        @QueryMap queries : Map<String, String>
    ): Response<Images>

    @GET("/api/?")
    suspend fun searchImages(
        @QueryMap searchQueries : Map<String, String>
    ): Response<Images>
}