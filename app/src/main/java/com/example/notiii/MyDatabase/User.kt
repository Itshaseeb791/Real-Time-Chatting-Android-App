package com.example.notiii.MyDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User_Table")
data class User(
    @PrimaryKey(autoGenerate = true)
    val Id : Int,
    val note: String
)
