package com.keepcoding.agdragonball.domain.interfaces

import com.keepcoding.agdragonball.domain.entities.Hero

interface HeroRepositoryInterface {
    sealed class DownloadHeroesResponse {
        data class Success(val heroes: List<Hero>) : DownloadHeroesResponse()
        data class Error(val message: String, val code: Int) : DownloadHeroesResponse()
    }

    suspend fun performDownloadHerosRequest(token: String): DownloadHeroesResponse
}