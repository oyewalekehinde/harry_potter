package com.harrypotter.app.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harrypotter.app.ui.Resource
import com.harrypotter.app.domain.model.Character
import com.harrypotter.app.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharactersViewModel(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    /**
     * A [StateFlow] that can be updated to provide a list of Character from the [ApiService]
     * or stored locally.
     */
    private  val _charactersResults = MutableStateFlow<Resource<List<Character>>>(Resource.Loading())
    val charactersResults: Flow<Resource<List<Character>>> get() = _charactersResults
    private var _completeList = emptyList<Character>()
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> get() = _searchQuery

    /**
     * Request the list of [Character] data.
     */
    fun retrieveData(){
      viewModelScope.launch {
          characterRepository.getCharacters().collect{
              _charactersResults.value = it
              if(it is Resource.Success){
                  setCompleteList(it.data)
              }
          }
      }
    }

    /**
     * Function to search/filter the list of [Character] data.
     */
    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value=newQuery
        val filteredList = _completeList.filter {
            (it.name?:"").contains(_searchQuery.value, ignoreCase = true) ||
                    (it.actor?:"").contains(_searchQuery.value, ignoreCase = true)
        }
      _charactersResults.value = Resource.Success(filteredList)
    }
    /**
     * Function to refresh the list of [Character] data.
     */
    fun refreshData(onRefreshDone: () -> Unit) {
            _searchQuery.value=""
            try {
                retrieveData()
            } finally {
                onRefreshDone()
            }
    }

    /**
     * Function to update the completeList.
     */
    fun setCompleteList(characters:List<Character>) {
    _completeList = characters
    }

}


