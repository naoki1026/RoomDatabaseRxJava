package com.example.roomdatabaserxjava.data.local.dao

import androidx.room.*
import com.example.roomdatabaserxjava.data.local.entity.StudentEntity
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single


@Dao
interface StudentDao {
    // 本来であれば戻り値はLongであるが、RxJavaを使用するため、Singleを使用する
    // Singleを使えば、値が1回しか流れてこない
    @Insert
    fun insert(studentEntity: StudentEntity) : Single<Long>

    @Update
    fun update(studentEntity: StudentEntity) : Single<Int>

    @Delete
    fun delete(studentEntity: StudentEntity) : Single<Int>

    @Query("SELECT * FROM students ORDER BY id DESC")
    fun getAllStudents() : Observable<List<StudentEntity>>
}