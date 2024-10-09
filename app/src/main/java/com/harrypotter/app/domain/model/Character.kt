package com.harrypotter.app.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

/**
 * Model object to store data about a character.
 */
@Entity(tableName = "character_table")
@JsonClass(generateAdapter = true)
data class Character (
    @PrimaryKey
    @Json(name = "id")
    val id : String,
    @Json(name = "name")
    val name: String?,
    @Json(name = "species")
    val species: String?,
    @Json(name = "actor")
    val actor: String?,
    @Json(name = "gender")
    val gender: String?,
    @Json(name = "house")
    val house: String?,
    @Json(name = "dateOfBirth")
    val dateOfBirth: String?,
    @Json(name = "yearOfBirth")
    val yearOfBirth: Long?,
    @Json(name = "wizard")
    val wizard: Boolean?,
    @Json(name = "ancestry")
    val ancestry: String?,
    @Json(name = "eyeColour")
    val eyeColour: String?,
    @Json(name = "hairColour")
    val hairColour: String?,
    @Json(name = "patronus")
    val patronus: String?,
    @Json(name = "hogwartsStudent")
    val hogwartsStudent: Boolean?,
    @Json(name = "hogwartsStaff")
    val hogwartsStaff: Boolean?,
    @Json(name = "alive")
    val alive: Boolean?,
    @Json(name = "image")
    val image: String?,
//    @Json(name = "alternate_actors")
//    val alternateActors: List<String>,
//    @Json(name = "alternate_names")
//    val alternateNames: List<String>,
//    @Json(name = "wand")
//    val wand: Wand,
) : Serializable


data class Wand (
    @Json(name = "wood")
    val wood: String,
    @Json(name = "core")
    val core:String,
    @Json(name = "length")
    val length: Long
) : Serializable