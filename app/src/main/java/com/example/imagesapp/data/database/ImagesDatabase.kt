package com.example.imagesapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ImagesEntity::class], version = 1, exportSchema = false)
@TypeConverters(ImagesTypeConvertor::class)
abstract class ImagesDatabase : RoomDatabase() {

    abstract fun imagesDao(): ImagesDao
}