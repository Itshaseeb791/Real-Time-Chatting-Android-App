package com.example.notiii.MyDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class MyDatabase :RoomDatabase(){
    abstract fun getDao() : Databasedao
    companion object{
        @Volatile
        private  var INSTANCE : MyDatabase?=null
        fun getDataBase(context: Context): MyDatabase {
            if (INSTANCE ==null){
                synchronized(this){
                    INSTANCE =
                        Room.databaseBuilder(
                            context.applicationContext,
                            MyDatabase::class.java
                        ,"MyDatabase"
                        ).build()
                }
            }
            return INSTANCE!!
        }

    }
}