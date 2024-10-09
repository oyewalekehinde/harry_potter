package com.harrypotter.app.data.remote.api

import com.harrypotter.app.domain.model.Character
import retrofit2.http.GET

interface ApiService {
    /**
     * Retrieve list of characters from the HarryPorter API.
     */
    @GET("characters")
    suspend fun getCharactersList() : List<Character>
}