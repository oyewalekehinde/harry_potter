package com.harrypotter.app.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.harrypotter.app.ui.ErrorMessage
import com.harrypotter.app.ui.NotFoundError
import com.harrypotter.app.ui.Resource
import com.harrypotter.app.domain.model.Character
import com.harrypotter.app.domain.repository.CharacterRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CharactersViewModelTest{

    // Rule to allow StateFlow to be tested synchronously
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val dispatcher=  StandardTestDispatcher()
    private val charactersRepository: CharacterRepository = mockk()
    private  val viewModel: CharactersViewModel = CharactersViewModel(charactersRepository)

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchItems should call getItems on repository`() = runTest {
        // Given
        val expectedCharacter = listOf(
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
            ),
        )
        val mockResponse: Flow<Resource<List<Character>>> = flowOf(Resource.Success(expectedCharacter))
        coEvery { charactersRepository.getCharacters() } returns mockResponse

        // When
        viewModel.retrieveData()
        dispatcher.scheduler.advanceUntilIdle()

        // Run the coroutines
        coVerify { charactersRepository.getCharacters() } // Behavioral verification
    }

    @Test
    fun `retrieveData should call repository and return success state`() = runTest  {
        // Mock repository response
        val expectedCharacters = listOf(
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
            ),
        )
        val mockResponse: Flow<Resource<List<Character>>> = flowOf(Resource.Success(expectedCharacters))

        // Set up the repository mock behavior
        coEvery { charactersRepository.getCharacters() } returns mockResponse

        // Call the method to retrieve data
        viewModel.retrieveData()
        dispatcher.scheduler.advanceUntilIdle()

        coVerify(exactly = 1) { charactersRepository.getCharacters() }

        assertEquals(Resource.Success(expectedCharacters), viewModel.charactersResults.first())
    }

    @Test
    fun `retrieveData should handle loading state`() = runTest {
        // Mock repository response
        val mockResponse: Flow<Resource<List<Character>>> = flowOf(Resource.Loading())

        // Set up the repository mock behavior
        coEvery { charactersRepository.getCharacters() } returns mockResponse

        // Call the method to retrieve data
        viewModel.retrieveData()
        dispatcher.scheduler.advanceUntilIdle()
        // Verify repository was called
        coVerify(exactly = 1) { charactersRepository.getCharacters() }

        // Verify loading state
        assertEquals(Resource.Loading<List<Character>>(), viewModel.charactersResults.first())
    }

    @Test
    fun `retrieveData should handle error state`() = runTest {
        // Mock repository response
        val mockResponse: Flow<Resource<List<Character>>> = flowOf(Resource.Error(NotFoundError("Not Found Error")))

        // Set up the repository mock behavior
        coEvery { charactersRepository.getCharacters() } returns mockResponse

        // Call the method to retrieve data
        viewModel.retrieveData()
        dispatcher.scheduler.advanceUntilIdle()
        // Verify repository was called
        coVerify(exactly = 1) { charactersRepository.getCharacters() }

        // Verify error state
        assertEquals(Resource.Error<ErrorMessage>(NotFoundError("Not Found Error")), viewModel.charactersResults.first())
    }
    @Test
    fun `onSearchQueryChanged updates search query and filters list`()  = runTest {
        // Mock a list of characters
        val mockCharacterList = listOf(
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
            ),
            Character(
                id = "1",
                name = "Hermione Granger",
                actor = "Emma Watson",
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
            ),
        )

        //Update the complete list for filtering
        viewModel.setCompleteList(mockCharacterList)

        // Perform search
        val searchQuery = "Harry"
        viewModel.onSearchQueryChanged(searchQuery)

        // Assert that the search query has been updated
        assertEquals(searchQuery, viewModel.searchQuery.value)

        // Capture the result from _charactersResults
        val filteredList = viewModel.charactersResults.first() as Resource.Success<List<Character>>
        val expectedFilteredList = listOf(mockCharacterList[0]) // Only Harry should match

        // Verify the list is correctly filtered
        assertEquals(expectedFilteredList, filteredList.data)
        }
}