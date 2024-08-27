package com.example.openinappassesment.Network

import retrofit2.http.GET

interface DashboardInterface {
    @GET("/api/v1/dashboardNew")
    suspend fun getDashboard() : retrofit2.Response<DashboardObject>
}