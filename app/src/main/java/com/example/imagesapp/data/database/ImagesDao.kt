package com.example.imagesapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface ImagesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImages(imagesEntity: ImagesEntity)

    @Query("SELECT * FROM images_table ORDER BY id ASC ")
    fun readImages(): Flow<List<ImagesEntity>>


}