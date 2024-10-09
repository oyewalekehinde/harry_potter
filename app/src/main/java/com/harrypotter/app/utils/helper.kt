package com.harrypotter.app.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.ui.graphics.Color
import com.harrypotter.app.domain.model.Character
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.Locale

fun String.capitalizeFirstLetter(): String {
    return if (this.isNotEmpty()) {
        this[0].uppercaseChar() + this.substring(1)
    } else {
        this
    }
}

fun String?.replaceEmptyString():String{
    return if(this==null){
        "---"
    }
   else if(this.isEmpty()){
        "---"
    }
    else{
        this.capitalizeFirstLetter()
    }
}

fun convertDate(inputDate: String?=""): String {
    if(inputDate ==null){
        return "---"
    }
    if(inputDate.isEmpty()){
        return "---"
    }
    val inputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

    val date = inputFormat.parse(inputDate)
    return outputFormat.format(date!!)
}
fun isOnline(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork
    val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
    return networkCapabilities != null &&
            networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}
fun encodeParameter(character: Character, moshi: Moshi):String{
    val jsonAdapter: JsonAdapter<Character> = moshi.adapter(Character::class.java)
    val characterJson = jsonAdapter.toJson(character)
    val encodedJson = URLEncoder.encode(characterJson, StandardCharsets.UTF_8.toString())
    return encodedJson
}

fun decodeParameter(characterModelJson:String?, moshi: Moshi):Character{
    val character =
        characterModelJson?.let {
            val decodedJson = URLDecoder.decode(it, StandardCharsets.UTF_8.toString())
            val adapter = moshi.adapter(Character::class.java)
            adapter.fromJson(decodedJson)
        }
    return character!!
}
fun getHouseColor(house:String): Color {
    return  when (house.lowercase()) {
        "griffindor" -> Color(0xFF740001)
        "slytherin" -> Color(0xFF1a472a)
        "ravenclaw" -> Color(0xFF0c1a40)
        else -> Color(0xFFeeb939)
    }
}