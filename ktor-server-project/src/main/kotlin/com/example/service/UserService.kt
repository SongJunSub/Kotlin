package com.example.service

import com.example.plugins.User

interface UserService {
    suspend fun findAll(): List<User>
    suspend fun create(user: User): User
}
