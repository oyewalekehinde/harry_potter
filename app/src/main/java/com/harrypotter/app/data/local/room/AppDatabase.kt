package com.harrypotter.app.data.local.room
import androidx.room.Database
import androidx.room.RoomDatabase
import com.harrypotter.app.domain.model.Character

@Database(entities = [Character::class], version = 2, exportSchema = true)
abstract class AppDatabase : RoomDatabase(){
    abstract  fun dao(): CharacterDao

    companion object {
        const val DATABASE_NAME = "character_db"
    }

}