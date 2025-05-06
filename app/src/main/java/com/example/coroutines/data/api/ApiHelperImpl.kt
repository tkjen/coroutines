package com.example.coroutines.data.api

import com.example.coroutines.data.model.ApiUser
import kotlinx.coroutines.flow.flow

class ApiHelperImpl(private val apiService: ApiService): ApiHelper {
    override fun getUsers() = flow {
        emit(apiService.getUsers())
    }

    override fun getMoreUsers() = flow {
        emit(apiService.getMoreUsers())
    }

    override fun getUsersWithError() = flow {
        emit(apiService.getUsersWithError())
    }
}