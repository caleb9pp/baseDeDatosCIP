package com.calebii.cipbase

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.calebii.cipbase.databinding.ItemStudentBinding


class StudentAdapter(private val students: List<Student>) :
    RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    inner class StudentViewHolder(val binding: ItemStudentBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val binding = ItemStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        with(holder.binding) {
            txtName.text = student.name
            txtEmail.text = student.email
            txtPhone.text = "Teléfono: ${student.phone}"
            txtStatus.text = student.status
        }
    }

    override fun getItemCount() = students.size
}
