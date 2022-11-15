package com.mozzart.grckikino.main.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mozzart.grckikino.global.BaseViewModel
import com.mozzart.grckikino.global.api.getErrorMessage
import com.mozzart.grckikino.global.data.KinoRepository
import com.mozzart.grckikino.main.data.Kino
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class KinosViewModel @Inject constructor(
    val repository: KinoRepository
): BaseViewModel(){

    private val TAG = "Kinos232323"


    val kinoMutableLiveData = MutableLiveData<ArrayList<Kino>>()
    val showProgress = MutableLiveData<Boolean>()
    private var job: Job? = null

    fun fetchKinos() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val kinos: Response<ArrayList<Kino>> = repository.fetchKinos()
            try {
                if (kinos != null) {
                    withContext(Dispatchers.Main) {
                        showProgress.value = false
                        if (kinos.isSuccessful) {
                            kinoMutableLiveData.value = kinos.body()
                            showProgress.value = false
                        }
                        else {
                            onError(getErrorMessage(kinos.errorBody()))
                        }

                    }
                } else {
                    Log.d(TAG, "fetchKinos: kinos == null")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showProgress.value = false
                    onError("")
                }
            }
        }
    }


    override fun clearMutableData() {
        TODO("Not yet implemented")
    }
}