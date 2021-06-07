package com.example.roomdatabaserxjava.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabaserxjava.R
import com.example.roomdatabaserxjava.data.local.entity.StudentEntity

class StudentAdapter() : RecyclerView.Adapter<StudentAdapter.MyViewHolder>() {

//    var studentList : List<StudentEntity> = emptyList()

    private val diffUtil = object : DiffUtil.ItemCallback<StudentEntity>() {
        override fun areItemsTheSame(oldItem: StudentEntity, newItem: StudentEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: StudentEntity, newItem: StudentEntity): Boolean {
            return oldItem == newItem
        }
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        private val id = itemView.findViewById<TextView>(R.id.idTextView)
        private val name = itemView.findViewById<TextView>(R.id.nameTextView)
        private val age = itemView.findViewById<TextView>(R.id.ageTextView)
        private val subject = itemView.findViewById<TextView>(R.id.subjectTextView)

        fun bind(data: StudentEntity) {
            id.text = data.id.toString()
            name.text = data.studentName
            age.text = data.age.toString()
            subject.text = data.subject
        }
    }

    private val asyncListDiffer = AsyncListDiffer(this, diffUtil)

    fun setList(newItem : List<StudentEntity>){
        asyncListDiffer.submitList((newItem))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.student_list_view, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val selectedStudent : StudentEntity = studentList[position]
        holder.bind(asyncListDiffer.currentList[position])
    }

    override fun getItemCount(): Int {
        return asyncListDiffer.currentList.size
//        return studentList.size
    }
}