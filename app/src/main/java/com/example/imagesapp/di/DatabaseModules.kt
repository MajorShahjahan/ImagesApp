package com.example.imagesapp.di

import android.content.Context
import androidx.room.Room
import com.example.imagesapp.data.database.ImagesDatabase
import com.example.imagesapp.util.Constants.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModules {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, ImagesDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun providesDao(database: ImagesDatabase) = database.imagesDao()
}