package com.keepcoding.agdragonball.domain.repository

import android.net.Credentials
import com.keepcoding.agdragonball.domain.User
import com.keepcoding.agdragonball.domain.interfaces.UserRepositoryInterface
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Request


const val BASE_URL = "https://dragonball.keepcoding.education/api/"

class UserRepository : UserRepositoryInterface{

    override suspend fun performLoginRequest(user: User): UserRepositoryInterface.LoginResponse {
        //Llamada a internet
        val client = OkHttpClient()
        val url = "${BASE_URL}auth/login"
        val credentials = okhttp3.Credentials.basic(user.name,user.password)
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", credentials)
            .post("".toRequestBody())
            .build()

        val response = client.newCall(request).execute() // se queda esperando a la rspuesta
        return if (response.isSuccessful) {
            val token = response.body.string() //no podemos leer la response.body dos veces, solo lo lee una vez
            println("Token: $token")
            UserRepositoryInterface.LoginResponse.Success(token)
        } else {
            UserRepositoryInterface.LoginResponse.Error(response.message, response.code)
        }

    }
}