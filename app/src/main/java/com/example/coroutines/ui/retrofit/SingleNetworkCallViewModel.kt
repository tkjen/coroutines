package com.example.coroutines.ui.retrofit

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coroutines.data.api.ApiHelper
import com.example.coroutines.data.local.DatabaseHelper
import com.example.coroutines.data.model.ApiUser
import com.example.coroutines.utils.Resource
import com.example.coroutines.utils.toUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleNetworkCallViewModel @Inject constructor(
    private val apiHelper: ApiHelper,
    private val databaseHelper: DatabaseHelper
) : ViewModel() {

    private val _users = MutableStateFlow<Resource<List<ApiUser>>>(Resource.Loading())
    val users: StateFlow<Resource<List<ApiUser>>> = _users

    fun fetchUsers() {
        viewModelScope.launch {
            apiHelper.getUsers()
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    _users.value = Resource.Error(e.message ?: "Unknown Error")
                }
                .collect { result ->
                    _users.value = Resource.Success(result)

                    val localUsers = result.map { it.toUser() }
                    databaseHelper.insertAll(localUsers)
                }
        }
    }
}
