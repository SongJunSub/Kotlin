package com.example.repository

import com.example.plugins.User

interface UserRepository {
    suspend fun findAll(): List<User>
    suspend fun create(user: User): User
}
