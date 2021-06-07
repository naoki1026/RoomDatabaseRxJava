package com.example.roomdatabaserxjava.data.repository

import com.example.roomdatabaserxjava.data.local.db.DatabaseService
import com.example.roomdatabaserxjava.data.local.entity.StudentEntity

//  RepositoryClass
//  データを取得する際の仲介を担っている
class StudentRepository(private val databaseService: DatabaseService) {

    fun insert(studentEntity: StudentEntity) = databaseService.studentDao().insert(studentEntity)

    fun update(studentEntity: StudentEntity) = databaseService.studentDao().update(studentEntity)

    fun delete(studentEntity: StudentEntity) = databaseService.studentDao().delete(studentEntity)

    fun getAllStudents() = databaseService.studentDao().getAllStudents()


}