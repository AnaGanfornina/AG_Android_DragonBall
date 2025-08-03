package com.keepcoding.agdragonball.data.dto


import com.google.gson.annotations.SerializedName


data class HeroDto(
    val id: String,
    @SerializedName("photo")
    val foto: String,
    var favorite: Boolean,
    val name: String,
    val description: String,
)