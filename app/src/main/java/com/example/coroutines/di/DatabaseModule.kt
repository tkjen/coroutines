package com.example.coroutines.di

import android.content.Context
import androidx.room.Room
import com.example.coroutines.data.local.AppDatabase
import com.example.coroutines.data.local.DatabaseHelper
import com.example.coroutines.data.local.DatabaseHelperImpl
import com.example.coroutines.data.local.dao.UserDao

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DatabaseModule {

    @Binds
    @Singleton
    abstract fun bindDatabaseHelper(
        databaseHelperImpl: DatabaseHelperImpl
    ): DatabaseHelper

    companion object {
        @Provides
        @Singleton
        // Hilt cần biết kiểu AppDatabase này đến từ đâu
        fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
            return Room.databaseBuilder(
                appContext,
                AppDatabase::class.java, // Class này cũng phải được resolve
                "learn-kotlin-coroutines"
            ).build()
        }

        @Provides
        @Singleton
        fun provideUserDao(appDatabase: AppDatabase): UserDao { // Tham số này cũng cần được resolve
            return appDatabase.userDao()
        }
    }
}