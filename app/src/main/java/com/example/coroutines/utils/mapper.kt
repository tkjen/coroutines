package com.example.coroutines.utils


import com.example.coroutines.data.model.ApiUser
import com.example.coroutines.data.local.entity.User

fun ApiUser.toUser(): User {
    return User(
        name = this.name,
        email = this.email,
        avatar = this.avatar
    )
}
