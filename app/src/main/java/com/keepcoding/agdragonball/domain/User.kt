package com.keepcoding.agdragonball.domain

import com.google.gson.Gson


data class User(val name: String = "", val password: String = ""){
    fun toJson(): String = Gson().toJson(this)

}
