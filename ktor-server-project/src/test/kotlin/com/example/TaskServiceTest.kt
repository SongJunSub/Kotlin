package com.example

import com.example.tasks.NewTask
import com.example.tasks.Task
import com.example.tasks.TaskService
import com.example.tasks.Tasks
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class TaskServiceTest {

    private lateinit var taskService: TaskService

    @Before
    fun setup() {
        taskService = TaskService()
        // Mock the transaction block to control database operations
        mockkStatic("org.jetbrains.exposed.sql.transactions.TransactionsKt")
    }

    @After
    fun teardown() {
        unmockkStatic("org.jetbrains.exposed.sql.transactions.TransactionsKt")
    }

    @Test
    fun `create should return a new task`() {
        val newTask = NewTask("Test Task", "Description")
        val expectedTask = Task(1, "Test Task", "Description", false)

        every { transaction(any(), any<() -> Task>()) } answers { callOriginal() }
        every { Tasks.insertAndGetId(any()) } returns org.jetbrains.exposed.sql.IntIdTable.EntityID(1, Tasks)
        every { Tasks.select(Tasks.id eq 1).map { any() }.singleOrNull() } returns expectedTask

        val createdTask = taskService.create(newTask)
        assertNotNull(createdTask)
        assertEquals(expectedTask.title, createdTask.title)
        assertEquals(expectedTask.description, createdTask.description)
    }

    @Test
    fun `getAll should return all tasks`() {
        val task1 = Task(1, "Task 1", "Desc 1", false)
        val task2 = Task(2, "Task 2", "Desc 2", true)

        every { transaction(any(), any<() -> List<Task>>()) } answers { callOriginal() }
        every { Tasks.selectAll().map { any() } } returns listOf(task1, task2)

        val tasks = taskService.getAll()
        assertEquals(2, tasks.size)
        assertEquals(task1.title, tasks[0].title)
        assertEquals(task2.title, tasks[1].title)
    }

    @Test
    fun `getById should return task if found`() {
        val expectedTask = Task(1, "Test Task", "Description", false)

        every { transaction(any(), any<() -> Task?>()) } answers { callOriginal() }
        every { Tasks.select(Tasks.id eq 1).map { any() }.singleOrNull() } returns expectedTask

        val foundTask = taskService.getById(1)
        assertNotNull(foundTask)
        assertEquals(expectedTask.title, foundTask.title)
    }

    @Test
    fun `getById should return null if not found`() {
        every { transaction(any(), any<() -> Task?>()) } answers { callOriginal() }
        every { Tasks.select(Tasks.id eq 99).map { any() }.singleOrNull() } returns null

        val foundTask = taskService.getById(99)
        assertNull(foundTask)
    }

    @Test
    fun `update should return updated task if found`() {
        val updatedInfo = NewTask("Updated Task", "Updated Description")
        val expectedTask = Task(1, "Updated Task", "Updated Description", false)

        every { transaction(any(), any<() -> Task?>()) } answers { callOriginal() }
        every { Tasks.update(any(), any()) } returns 1
        every { Tasks.select(Tasks.id eq 1).map { any() }.singleOrNull() } returns expectedTask

        val updatedTask = taskService.update(1, updatedInfo)
        assertNotNull(updatedTask)
        assertEquals(expectedTask.title, updatedTask.title)
        assertEquals(expectedTask.description, updatedTask.description)
    }

    @Test
    fun `update should return null if not found`() {
        val updatedInfo = NewTask("Updated Task", "Updated Description")

        every { transaction(any(), any<() -> Task?>()) } answers { callOriginal() }
        every { Tasks.update(any(), any()) } returns 0
        every { Tasks.select(Tasks.id eq 99).map { any() }.singleOrNull() } returns null

        val updatedTask = taskService.update(99, updatedInfo)
        assertNull(updatedTask)
    }

    @Test
    fun `delete should return true if task deleted`() {
        every { transaction(any(), any<() -> Boolean>()) } answers { callOriginal() }
        every { Tasks.deleteWhere(any()) } returns 1

        val result = taskService.delete(1)
        assertTrue(result)
    }

    @Test
    fun `delete should return false if task not found`() {
        every { transaction(any(), any<() -> Boolean>()) } answers { callOriginal() }
        every { Tasks.deleteWhere(any()) } returns 0

        val result = taskService.delete(99)
        assertFalse(result)
    }
}