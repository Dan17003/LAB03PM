package com.mendoza.danna.laboratoriocalificado03.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mendoza.danna.laboratoriocalificado03.data.Teacher
import com.mendoza.danna.laboratoriocalificado03.databinding.ItemTeacherBinding

class TeacherAdapter(
    private var teachers: List<Teacher>,
    private val onTeacherClick: (Teacher) -> Unit,
    private val onTeacherLongClick: (Teacher) -> Unit
) : RecyclerView.Adapter<TeacherAdapter.TeacherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherViewHolder {
        val binding = ItemTeacherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TeacherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TeacherViewHolder, position: Int) {
        holder.bind(teachers[position])
    }

    override fun getItemCount(): Int = teachers.size

    fun updateData(newTeachers: List<Teacher>) {
        this.teachers = newTeachers
        notifyDataSetChanged()
    }

    inner class TeacherViewHolder(private val binding: ItemTeacherBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(teacher: Teacher) {
            binding.tvTeacherName.text = teacher.name
            binding.tvTeacherLastName.text = teacher.lastName

            Glide.with(binding.root.context)
                .load(teacher.imageUrl)
                .into(binding.ivTeacherPhoto)

            // Configurar el listener para el click simple
            binding.root.setOnClickListener {
                onTeacherClick(teacher)
            }

            // Configurar el listener para el click largo
            binding.root.setOnLongClickListener {
                onTeacherLongClick(teacher)
                true // Indica que el evento ha sido consumido
            }
        }
    }
}