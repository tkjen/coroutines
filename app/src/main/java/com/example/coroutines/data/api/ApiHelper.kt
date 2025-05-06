package com.example.coroutines.data.api

import com.example.coroutines.data.model.ApiUser
import kotlinx.coroutines.flow.Flow

interface ApiHelper {
    fun getUsers(): Flow<List<ApiUser>>

     fun getMoreUsers(): Flow<List<ApiUser>>

     fun getUsersWithError(): Flow<List<ApiUser>>
}