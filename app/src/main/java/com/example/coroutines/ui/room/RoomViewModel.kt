package com.example.coroutines.ui.room

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coroutines.data.local.DatabaseHelper
import com.example.coroutines.data.local.entity.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor (
    // Hilt/KSP cần biết kiểu 'DatabaseHelper' này đến từ đâu
    private val dbHelper: DatabaseHelper
) : ViewModel() {
    private val TAG = "RoomViewModel_Logs"
    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            try {
                val users = dbHelper.getUsers()
                // Xử lý danh sách user (ví dụ: cập nhật LiveData/StateFlow)
                // _usersStateFlow.value = users
            } catch (e: Exception) {
                // Xử lý lỗi (ví dụ: cập nhật trạng thái lỗi)
                // _errorStateFlow.value = e.message
                e.printStackTrace() // Giữ lại để debug nếu cần
            }
        }
    }

    fun insertUser(user: User) {
        viewModelScope.launch {
            try {
                Log.d(TAG, "Attempting to insert user: $user")
                dbHelper.insertUsers(user)
                // Log sau khi gọi insert thành công (nếu không có lỗi)
                Log.i(TAG, "Successfully inserted user: $user")
            } catch (e: Exception) {
                // Xử lý lỗi
                Log.e(TAG, "Error inserting user: $user", e)
            }
        }
    }
}