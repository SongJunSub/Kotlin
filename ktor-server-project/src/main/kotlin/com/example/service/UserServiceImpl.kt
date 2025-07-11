package com.example.service

import com.example.plugins.User
import com.example.repository.UserRepository

class UserServiceImpl(private val repository: UserRepository) : UserService {
    override suspend fun findAll(): List<User> = repository.findAll()
    override suspend fun create(user: User): User = repository.create(user)
}
