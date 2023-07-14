package com.example.suitmedia.Network

import com.example.suitmedia.Data.Repository

object Injection {
    fun provideRepository(): Repository {
        val apiService = ApiConfig.getApiService()
        return Repository.getInstance(apiService)
    }
}