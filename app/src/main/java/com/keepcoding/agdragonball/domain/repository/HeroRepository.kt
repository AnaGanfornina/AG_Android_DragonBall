package com.keepcoding.agdragonball.domain.repository

import com.google.gson.Gson
import com.keepcoding.agdragonball.data.dto.HeroDto
import com.keepcoding.agdragonball.domain.entities.Hero
import com.keepcoding.agdragonball.domain.interfaces.HeroRepositoryInterface
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

class HeroRepository: HeroRepositoryInterface {

    override suspend fun performDownloadHerosRequest(token: String): HeroRepositoryInterface.DownloadHeroesResponse {
        val client = OkHttpClient()
        val url = "${BASE_URL}heros/all"
        val credentials = "Bearer $token"

        val body = FormBody.Builder().add("name", "").build()
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", credentials)
            .post(body)
            .build()

        val response = client.newCall(request).execute()
        return if (response.isSuccessful) {
            val responseBody = response.body?.string() ?: ""
            val json = Gson().fromJson(responseBody, Array<HeroDto>::class.java)
            HeroRepositoryInterface.DownloadHeroesResponse.Success(json.toList().map { Hero(it.id,it.foto, it.name)  })
        } else {
            HeroRepositoryInterface.DownloadHeroesResponse.Error(response.message, response.code)
        }
    }
}