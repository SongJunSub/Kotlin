package com.example.tasks

import org.jetbrains.exposed.sql.Table

object Tasks : Table() {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 255)
    val description = varchar("description", 1024)
    val completed = bool("completed")

    override val primaryKey = PrimaryKey(id)
}

data class Task(val id: Int, val title: String, val description: String, val completed: Boolean)