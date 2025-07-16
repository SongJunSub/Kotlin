package com.example.tasks

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class TaskService {

    fun getAll(): List<Task> = transaction {
        Tasks.selectAll().map { toTask(it) }
    }

    fun getById(id: Int): Task? = transaction {
        Tasks.select { Tasks.id eq id }.map { toTask(it) }.singleOrNull()
    }

    fun create(task: NewTask): Task {
        val id = transaction {
            Tasks.insertAndGetId {
                it[title] = task.title
                it[description] = task.description
                it[completed] = false
            }
        }
        return getById(id.value)!!
    }

    fun update(id: Int, task: NewTask): Task? {
        transaction {
            Tasks.update({ Tasks.id eq id }) {
                it[title] = task.title
                it[description] = task.description
            }
        }
        return getById(id)
    }

    fun delete(id: Int): Boolean = transaction {
        Tasks.deleteWhere { Tasks.id eq id } > 0
    }

    private fun toTask(row: ResultRow): Task = Task(
        id = row[Tasks.id],
        title = row[Tasks.title],
        description = row[Tasks.description],
        completed = row[Tasks.completed]
    )
}

data class NewTask(val title: String, val description: String)