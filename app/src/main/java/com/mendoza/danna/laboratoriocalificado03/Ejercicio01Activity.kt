package com.mendoza.danna.laboratoriocalificado03

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mendoza.danna.laboratoriocalificado03.adapter.TeacherAdapter
import com.mendoza.danna.laboratoriocalificado03.data.Teacher
import com.mendoza.danna.laboratoriocalificado03.databinding.ActivityEjercicio01Binding
import com.mendoza.danna.laboratoriocalificado03.network.RetrofitClient
import kotlinx.coroutines.launch

class Ejercicio01Activity : AppCompatActivity() {

    private lateinit var binding: ActivityEjercicio01Binding
    private lateinit var teacherAdapter: TeacherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEjercicio01Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        fetchTeachers()
    }

    private fun setupRecyclerView() {
        teacherAdapter = TeacherAdapter(
            teachers = emptyList(),
            onTeacherClick = { teacher ->
                // Lógica para click simple: llamar al número de teléfono
                callTeacher(teacher)
            },
            onTeacherLongClick = { teacher ->
                // Lógica para click largo: enviar un correo electrónico
                emailTeacher(teacher)
            }
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = teacherAdapter
    }

    private fun fetchTeachers() {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.getTeachers()
                if (response.isSuccessful) {
                    response.body()?.teachers?.let { teachers ->
                        teacherAdapter.updateData(teachers)
                    }
                } else {
                    showError("Error al cargar los datos de los profesores.")
                }
            } catch (e: Exception) {
                showError("Error de conexión: ${e.message}")
            }
        }
    }

    private fun callTeacher(teacher: Teacher) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:${teacher.phone}")
        }
        startActivity(intent)
    }

    private fun emailTeacher(teacher: Teacher) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // Solo las apps de email deben manejar esto
            putExtra(Intent.EXTRA_EMAIL, arrayOf(teacher.email))
            putExtra(Intent.EXTRA_SUBJECT, "Contacto desde la App")
        }
        // Verificar si hay una app que pueda manejar el intent
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            showError("No se encontró una aplicación de correo.")
        }
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}