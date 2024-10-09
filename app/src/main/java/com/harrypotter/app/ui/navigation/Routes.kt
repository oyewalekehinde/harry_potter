package com.harrypotter.app.ui.navigation

sealed class Routes (val route:String){

    data object HomeScreen : Routes("homeScreen")

    data object ViewCharacter : Routes("viewCharacter")
}
