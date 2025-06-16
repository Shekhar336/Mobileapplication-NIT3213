package com.example.myassssmentapplication.data.api

import com.example.myassssmentapplication.data.model.DashboardResponse
import com.example.myassssmentapplication.data.model.LoginResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Body

interface ApiService {
    @POST("sydney/auth")
    suspend fun login(@Body loginRequest: Map<String, String>): Response<LoginResponse>

    @GET("dashboard/{keypass}")
    suspend fun getDashboard(@Path("keypass") keypass: String): Response<DashboardResponse>
} 