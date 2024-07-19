package com.example.notiii.MyDatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface Databasedao {
    @Insert
    suspend fun addData(user: User)
    @Update
    suspend fun Updatedata(user: User)
    @Delete
    suspend fun deleteData(user: User)
    @Query("SELECT * FROM User_Table ORDER BY Id DESC")
      fun readAll():LiveData<List<User>>
}