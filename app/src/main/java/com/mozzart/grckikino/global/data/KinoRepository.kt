package com.mozzart.grckikino.global.data

import com.mozzart.grckikino.global.api.Api
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class KinoRepository @Inject constructor(private val api: Api){

    //Fetch Kinos
    suspend fun fetchKinos() = api.getKinos()
}