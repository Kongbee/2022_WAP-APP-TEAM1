package com.wap.domain.datasource

import com.wap.domain.entity.ToDo
import kotlinx.coroutines.flow.Flow

interface ToDoDataSource {

    suspend fun createToDo(toDo: ToDo)

    fun fetchAllToDoList(): Flow<List<ToDo>>

    fun fetchToDoListById(toDoId: Long): Flow<ToDo>

    suspend fun updateToDo(toDo: ToDo)

    suspend fun deleteToDo(toDo: ToDo)
}