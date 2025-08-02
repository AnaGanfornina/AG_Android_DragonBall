package com.keepcoding.agdragonball.domain.interfaces

import com.keepcoding.agdragonball.domain.User

interface UserRepositoryInterface {

    sealed class LoginResponse {
        data class Success(val token: String) : LoginResponse()
        data class Error(val message: String, val code: Int) : LoginResponse()
    }

    suspend fun performLoginRequest(user: User): LoginResponse


}