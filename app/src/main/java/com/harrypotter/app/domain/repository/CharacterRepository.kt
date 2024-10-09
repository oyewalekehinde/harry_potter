package com.harrypotter.app.domain.repository

import com.harrypotter.app.ui.Resource
import com.harrypotter.app.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun getCharacters() : Flow<Resource<List<Character>>>
}