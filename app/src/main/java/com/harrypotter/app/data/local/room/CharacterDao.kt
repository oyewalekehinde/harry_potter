package com.harrypotter.app.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import com.harrypotter.app.domain.model.Character

@Dao
interface CharacterDao{
    /**
    * Retrieve stored [Character]s.
    */
    @Query("SELECT * FROM  character_table")
    fun getAllCharacters() : Flow<List<Character>>

    /**
    * Store new [Character] List.
    */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(characters: List<Character>)
}