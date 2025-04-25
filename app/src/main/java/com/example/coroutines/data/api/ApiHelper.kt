package com.example.coroutines.data.api

import com.example.coroutines.data.model.ApiUser

interface ApiHelper {
    suspend fun getUsers(): List<ApiUser>

    suspend fun getMoreUsers(): List<ApiUser>

    suspend fun getUsersWithError(): List<ApiUser>
}