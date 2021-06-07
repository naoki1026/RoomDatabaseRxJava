package com.example.roomdatabaserxjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.AdapterView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.example.roomdatabaserxjava.data.local.entity.StudentEntity
import com.example.roomdatabaserxjava.ui.StudentViewModel
import com.example.roomdatabaserxjava.ui.adapter.StudentAdapter
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel : StudentViewModel
    private lateinit var fabAddStudent : ExtendedFloatingActionButton
    private lateinit var mAdapter : StudentAdapter
    private lateinit var mRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fabAddStudent = findViewById(R.id.floatingActionButton)

        // ViewModelProviderは、ViewModelのインスタンスを外部へ提供している
        // ViewModelインスタンスの取得
        // https://developer.android.com/reference/android/arch/lifecycle/ViewModelProvider.AndroidViewModelFactory
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))
            .get(StudentViewModel::class.java)

        observers()

        fabAddStudent.setOnClickListener{
            showAddStudentDialog()
        }

        mRecyclerView = findViewById(R.id.recyclerViewStudent)
        mAdapter = StudentAdapter()

        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(this)

    }

    private fun observers(){
        viewModel.isLoading.observe(this, androidx.lifecycle.Observer {

        })

        viewModel.isError.observe(this, androidx.lifecycle.Observer {

        })

        viewModel.isSuccess.observe(this, androidx.lifecycle.Observer {

        })

        viewModel.studentList.observe(this, androidx.lifecycle.Observer {
            mAdapter.setList(it)
        })
    }

    private fun showAddStudentDialog(){
        val dialog = MaterialDialog(this)
            .cornerRadius(8f)
            .cancelable(false)
            .customView(R.layout.student_view_dialog)
//        val customView = dialog.customView()
        val customView = dialog.getCustomView()

        dialog.positiveButton(R.string.dialog_save) {
            val name = customView.findViewById<TextInputEditText>(R.id.studentName)
            val age = customView.findViewById<TextInputEditText>(R.id.studentAge)
            val subject = customView.findViewById<TextInputEditText>(R.id.studentSubject)

            viewModel.studentName.value = name.text.toString()
            viewModel.studentAge.value = age.text.toString().toInt()
            viewModel.studentSubject.value = subject.text.toString()

            viewModel.insert()

        }
        dialog.negativeButton {
            dialog.dismiss()

        }
        dialog.show()
    }
}