package com.example.roomdatabaserxjava.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.roomdatabaserxjava.data.local.dao.StudentDao
import com.example.roomdatabaserxjava.data.local.entity.StudentEntity

@Database(entities = [StudentEntity::class], version = 1, exportSchema = false)
abstract class DatabaseService : RoomDatabase() {
    abstract fun studentDao() : StudentDao

    companion object {

        // 異なるアクセス間で値が変わる可能性があることを意味する
        // 複数のスレッドからのアクセスをスレッドセーフにしている
        @Volatile
        private var INSTANCE : DatabaseService? = null

        fun getInstance(context : Context) : DatabaseService {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DatabaseService::class.java,
                        "student_demo"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }

        }
    }
}