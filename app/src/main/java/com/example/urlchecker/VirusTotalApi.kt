package com.example.urlchecker

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface VirusTotalApi {
    @GET("url/report")
    fun checkUrl(
        @Query("apikey") apiKey: String,
        @Query("resource") url: String
    ): Call<VirusTotalResponse>
}

data class VirusTotalResponse(
    val verbose_msg: String,
    val url: String,
    val positives: Int,
    val total: Int,
    val scans: Map<String, ScanResult>
)

data class ScanResult(
    val detected: Boolean,
    val result: String
)