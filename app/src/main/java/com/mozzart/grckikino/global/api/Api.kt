package com.mozzart.grckikino.global.api

import com.mozzart.grckikino.BuildConfig
import com.mozzart.grckikino.main.data.Kino
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {

    companion object {
        const val BASE_URL = BuildConfig.BASE_URL
    }

    @GET("1100/upcoming/20")
    suspend fun getKinos(): Response<ArrayList<Kino>>

    @POST("1100/{drawId}")
    suspend fun getKino()


//
}