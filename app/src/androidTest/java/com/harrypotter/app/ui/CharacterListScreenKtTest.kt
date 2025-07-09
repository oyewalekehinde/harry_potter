package com.harrypotter.app.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.harrypotter.app.di.testModule

import com.harrypotter.app.domain.model.Character
import com.harrypotter.app.viewModels.CharactersViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.koin.test.KoinTestRule


@RunWith(AndroidJUnit4::class)
class CharacterListScreenKtTest{
    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(testModule)
    }

    @get:Rule
    val composeTestRule = createComposeRule()

    // Mock module to be used for the test
    private val testModule = module {
        viewModel { CharactersViewModel(get()) }
    }

    @Before
    fun setup() {
        stopKoin() // Stop Koin before the test starts
    }
    // Mock data for testing
    val mockCharacters = listOf(
        Character(
            id = "1",
            name = "Harry Potter",
            actor = "Daniel Radcliffe",
            dateOfBirth = "31-07-1980",
            alive = true,
            yearOfBirth = 1980,
            wizard = true,
            ancestry = "half-blood",
            eyeColour = "green",
            hairColour = "black",
            patronus = "stag",
            hogwartsStudent = true,
            hogwartsStaff = false,
            image = "https://ik.imagekit.io/hpapi/harry.jpg",
            gender = "male",
            house = "Gryffindor",
            species = "human"
        ),)
    @Test
    fun testScreenDisplaysCorrectly() {
//        val navController = TestNavHostController(composeTestRule.activity)
        // Set the content to be tested
        composeTestRule.setContent {
         CharacterListScreen( onItemClick = {} )
        }

//        // Assertions on UI components
//        composeTestRule
//            .onNodeWithText("Next")
//            .assertIsDisplayed()
//            .assertIsEnabled()
//            .performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Harry Potter").assertIsDisplayed()
//        composeTestRule
//            .onNodeWithText("Log in")
//            .assertIsDisplayed()
//            .assertIsEnabled()
//            .performClick()
    }
}