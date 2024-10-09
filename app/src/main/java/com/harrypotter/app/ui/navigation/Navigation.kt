package com.harrypotter.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.harrypotter.app.ui.CharacterDetailScreen
import com.harrypotter.app.ui.CharacterListScreen
import com.harrypotter.app.utils.decodeParameter
import com.harrypotter.app.utils.encodeParameter
import com.squareup.moshi.Moshi
import org.koin.compose.koinInject

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val moshi: Moshi = koinInject()
    NavHost(navController = navController, startDestination = Routes.HomeScreen.route) {
        composable(route = Routes.HomeScreen.route) {
            CharacterListScreen(
                onItemClick = {character->
                    navController.navigate(Routes.ViewCharacter.route+"/${encodeParameter(character,moshi)}")
                }
            )
        }
        composable(
            route = Routes.ViewCharacter.route + "/{character}",
            arguments = listOf(navArgument("character") { type = NavType.StringType }),
        ) { backStackEntry ->
            val characterModelJson = backStackEntry.arguments?.getString("character")
            CharacterDetailScreen(decodeParameter(characterModelJson,moshi), back = {navController
                .popBackStack()} )
        }
    }
}