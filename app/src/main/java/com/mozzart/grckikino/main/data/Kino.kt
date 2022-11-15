package com.mozzart.grckikino.main.data

import com.google.gson.annotations.SerializedName

data class Kino(
    @SerializedName("gameId")
    val gameID: String,
    @SerializedName("drawId")
    val drawID: String,
    @SerializedName("drawTime")
    val drawTime: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("visualDraw")
    val visualDraw: String
)
