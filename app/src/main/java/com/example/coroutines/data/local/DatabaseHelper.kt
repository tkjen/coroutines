
package com.example.coroutines.data.local
import com.example.coroutines.data.local.entity.User


interface DatabaseHelper {

    suspend fun getUsers(): List<User>

    suspend fun insertUsers(user: User)
    suspend fun insertAll(users: List<User>)

}