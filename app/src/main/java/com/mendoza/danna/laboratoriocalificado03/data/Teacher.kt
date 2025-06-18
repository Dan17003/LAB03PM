package com.mendoza.danna.laboratoriocalificado03.data

import com.google.gson.annotations.SerializedName

// Clase principal que representa la respuesta completa de la API
data class TeacherResponse(
    @SerializedName("teachers") val teachers: List<Teacher>
)

// Clase que representa a un profesor individual
data class Teacher(
    @SerializedName("name") val name: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("email") val email: String,
    @SerializedName("imageUrl") val imageUrl: String
)