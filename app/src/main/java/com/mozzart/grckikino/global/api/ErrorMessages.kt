package com.mozzart.grckikino.global.api

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import org.json.JSONObject

fun getErrorMessage(errorBody: ResponseBody?): String{
    if(errorBody != null){
        return try {
            val gson = Gson()
            val jObjError = JSONObject(errorBody.string())
            Log.e("Authentification232323", "getErrorBody: ${Gson().toJson(jObjError)}")
            val messages = jObjError.getString("messages")
            Log.e("Authentification232323", "getErrorMessage: $messages")
            val messageList = gson.fromJson<List<String>>(messages, object : TypeToken<List<String>>() {}.type)
            if (messageList.isNotEmpty()) {
                messageList[0]
            } else {
                ""
            }
        } catch (e: Exception) {
            e.localizedMessage
        }
    }else{
        return ""
    }
}
