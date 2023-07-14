package com.example.suitmedia.Network

import com.example.suitmedia.Response.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    suspend fun getListUsers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): Response
}