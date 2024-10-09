package com.harrypotter.app.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.harrypotter.app.R
import com.harrypotter.app.ui.ErrorMessage
import com.harrypotter.app.ui.NetworkError
import com.harrypotter.app.ui.NetworkTimeoutError
import com.harrypotter.app.ui.NotFoundError
import com.harrypotter.app.ui.ServerError
import com.harrypotter.app.viewModels.CharactersViewModel


@Composable
fun ErrorComposable(error: ErrorMessage, charactersViewModel: CharactersViewModel){
    val errorMessage = when (error) {
        is NetworkTimeoutError -> stringResource(R.string.networkTimeout)
        is NetworkError -> stringResource(R.string.networkError)
        is ServerError -> stringResource(R.string.serverError)
        is NotFoundError -> stringResource(R.string.dataNotFound)
        else -> stringResource(R.string.unknownError)
    }
    Column(modifier = Modifier.fillMaxSize().padding(20.dp), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text( errorMessage, style =
        MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.W500,
        ),
            textAlign = TextAlign.Center
        )

        Button(
            modifier = Modifier.padding(top = 20.dp),
            onClick = {charactersViewModel.refreshData {  }}) {
            Text(stringResource(R.string.retry))
        }
    }
}