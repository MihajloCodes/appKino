package com.mozzart.grckikino.global

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson

abstract class BaseViewModel: ViewModel() {

    private val gson: Gson = Gson()

    val errorMessage = MutableLiveData<String?>()

    fun printObject(src: Any?): String{
        return gson.toJson(src)
    }

    fun onError(message: String) {
        errorMessage.value = message
        errorMessage.value = null
    }

    abstract fun clearMutableData()
}