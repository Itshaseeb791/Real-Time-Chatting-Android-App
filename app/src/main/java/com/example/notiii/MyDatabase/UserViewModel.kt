package com.example.notiii.MyDatabase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Delete
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application):AndroidViewModel(application) {
      val readAllData : LiveData<List<User>>
    private val repository: UserRepository
    init {
        val userDeo = MyDatabase.getDataBase(application).getDao()
        repository= UserRepository(userDeo)
        readAllData=repository.readAllData
    }
    fun addUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }
    fun Updatadata(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.UpdateData(user)
        }
    }
    fun DeleteData(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.DeleteData(user)
        }
    }
}