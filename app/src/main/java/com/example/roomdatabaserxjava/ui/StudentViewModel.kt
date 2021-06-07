package com.example.roomdatabaserxjava.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.roomdatabaserxjava.data.local.db.DatabaseService
import com.example.roomdatabaserxjava.data.local.entity.StudentEntity
import com.example.roomdatabaserxjava.data.repository.StudentRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers


// AndroidViewModelを継承して、Contextを必要とするため、Applicationをコンストラクタとして渡す
class StudentViewModel(private val app : Application) : AndroidViewModel(app) {

    private val studentRepository = StudentRepository(DatabaseService.getInstance(app))
    val studentName = MutableLiveData<String>()
    val studentAge = MutableLiveData<Int>()
    val studentSubject = MutableLiveData<String>()
    private val studentId = MutableLiveData<Long>()
    private val TAG = "MainActivity"
    val studentList = MutableLiveData<List<StudentEntity>>()
    val isLoading = MutableLiveData<Boolean>()
    val isError = MutableLiveData<String>()
    val isSuccess = MutableLiveData<Boolean>()

    // Rxライブラリより提供されており、まとめてDisposeするためのクラス
    // 自身をDisposeすることで登録されている要素をまとめてDisposeする
    private val compositeDisposal = CompositeDisposable()

    init {
        getAllStudents()
    }

    fun insert(){

        isLoading.value = true

        compositeDisposal.add (
            studentRepository.insert(createInsertStudentEntity())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        Log.d(TAG, "Insert : $it")
                        isLoading.value = false
                    },
                    {
                        Log.e(TAG, it.toString())
                        isLoading.value = false
                    }
                )
        )
    }

    fun update(){

        isLoading.value = true

        compositeDisposal.add (
            studentRepository.update(createStudentEntity())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        Log.d(TAG, "Update : $it")
                        isLoading.value = false
                        isSuccess.value = true
                    },
                    {
                        Log.e(TAG, it.toString())
                        isLoading.value = false
                        isError.value = it.message
                    }
                )
        )
    }

    fun delete(){

        isLoading.value = true

        compositeDisposal.add (
            studentRepository.delete(createStudentEntity())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        Log.d(TAG, "Delete : $it")
                        isLoading.value = false
                        isSuccess.value = true
                    },
                    {
                        Log.e(TAG, it.toString())
                        isLoading.value = false
                        isError.value = it.message
                    }
                )

        )
    }

    fun getAllStudents(){

        isLoading.value = true

        compositeDisposal.add(
            studentRepository.getAllStudents()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        Log.d(TAG, "GetAll : $it")
                        studentList.value = it
                        isLoading.value = false
                        isSuccess.value = true
                    },
                    {
                        Log.e(TAG, it.toString())
                        isLoading.value = false
                        isError.value = it.message
                    }
                )
        )
    }

    private fun createInsertStudentEntity(): StudentEntity {
        return StudentEntity(
            studentName = studentName.value.toString(),
            age = studentAge.value.toString().toInt(),
            subject = studentSubject.value.toString()
        )
    }

    private fun createStudentEntity() : StudentEntity {
        return StudentEntity(
            id = studentId.value.toString().toLong(),
            studentName = studentName.value.toString(),
            age = studentAge.value.toString().toInt(),
            subject = studentSubject.value.toString()
        )
    }

}