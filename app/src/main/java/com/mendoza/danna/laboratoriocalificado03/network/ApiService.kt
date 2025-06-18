package com.mendoza.danna.laboratoriocalificado03.network

import com.mendoza.danna.laboratoriocalificado03.data.TeacherResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("list/teacher")
    suspend fun getTeachers(): Response<TeacherResponse>
}