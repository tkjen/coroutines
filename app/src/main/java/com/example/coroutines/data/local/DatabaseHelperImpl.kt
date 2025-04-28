
package com.example.coroutines.data.local
import com.example.coroutines.data.local.AppDatabase
import com.example.coroutines.data.local.DatabaseHelper
import com.example.coroutines.data.local.entity.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseHelperImpl @Inject constructor ( private val appDatabase: AppDatabase) :
    DatabaseHelper {

    override suspend fun getUsers(): List<User> = appDatabase.userDao().getAll()

    override suspend fun insertAll(users: List<User>) = appDatabase.userDao().insertAll(users)

    override suspend fun insertUsers(user: User) = appDatabase.userDao().insertUser(user)


}