package com.example.taklist.DataStorage

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var taskName: String,
    var isCompleted: Boolean = false
)
