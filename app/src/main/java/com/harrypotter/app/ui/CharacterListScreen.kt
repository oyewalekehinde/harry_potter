package com.harrypotter.app.ui
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.harrypotter.app.R
import com.harrypotter.app.domain.model.Character
import com.harrypotter.app.viewModels.CharactersViewModel
import com.harrypotter.app.ui.composables.CharacterItem
import com.harrypotter.app.ui.composables.ErrorComposable
import com.harrypotter.app.ui.composables.ShimmerEffect
import org.koin.androidx.compose.koinViewModel

@Composable
fun CharacterListScreen(charactersViewModel: CharactersViewModel = koinViewModel<CharactersViewModel> (),  onItemClick:(Character)->Unit){
    val alreadyLaunched = rememberSaveable { mutableStateOf(false) }
    val charactersState by charactersViewModel.charactersResults.collectAsState(initial = Resource.Loading())
    val searchQuery by charactersViewModel.searchQuery.collectAsStateWithLifecycle()
    var isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)

    // Because Compose offers out of the box config,
    // let's have control over alreadyLaunched by using rememberSaveable making our app stable,
    if (!alreadyLaunched.value) {
        LaunchedEffect(Unit) {
            alreadyLaunched.value = true
           charactersViewModel.retrieveData()
        }
    }
    Scaffold { innerPadding -> Box(modifier = Modifier.padding(innerPadding)){
        when(charactersState){
            is Resource.Loading-> LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(12) { _ ->
                    Column {
                        ShimmerEffect(height = 100.dp)
                        Spacer(modifier = Modifier.height(15.dp))
                    }
                }
            }
            is Resource.Success -> {
                val characters: List<Character> = (charactersState as Resource.Success<List<Character>>).data
                Column {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { charactersViewModel.onSearchQueryChanged(it)},
                        placeholder = { Text(stringResource(R.string.searchText), maxLines = 1) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp, top = 20.dp),
                        singleLine = true,
                        shape = RoundedCornerShape(6.dp),
                    )
                    SwipeRefresh( state = swipeRefreshState,
                        onRefresh = {
                            isRefreshing = true
                            charactersViewModel.refreshData {
                                isRefreshing = false
                            }
                        },
                        modifier = Modifier.fillMaxSize()){

                        if(characters.isEmpty()){
                            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center,modifier = Modifier.fillMaxSize()) {
                                Text( stringResource(R.string.emptyList), style =
                                MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.onBackground,
                                    fontWeight = FontWeight.W500,
                                ),
                                    textAlign = TextAlign.Center
                                )
                            }}else{
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(characters.size) { index ->
                                 Column {
                                    if(index==0){
                                        Spacer(modifier = Modifier.height(15.dp))}

                                    CharacterItem(characters[index], onClick = {
                                        onItemClick(characters[index])

                                    })}
                                    Spacer(modifier = Modifier.height(15.dp))
                                }
                            }
                        }
                    }
                }}

            is Resource.Error -> {
                val error =( charactersState as Resource.Error)
                ErrorComposable(error.message, charactersViewModel)
        }}
    }
    }
}
