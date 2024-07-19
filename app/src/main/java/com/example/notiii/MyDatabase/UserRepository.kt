package com.example.notiii.MyDatabase

import androidx.lifecycle.LiveData
import com.example.notiii.MyDatabase.Databasedao
import com.example.notiii.MyDatabase.User

class UserRepository(private  val databasedao: Databasedao) {
    val readAllData : LiveData<List<User>> = databasedao.readAll()
    suspend fun addUser(user: User){
    databasedao.addData(user)
    }
    suspend fun UpdateData(user: User){
        databasedao.Updatedata(user)
    }
    suspend fun DeleteData(user: User){
        databasedao.deleteData(user)
    }
}