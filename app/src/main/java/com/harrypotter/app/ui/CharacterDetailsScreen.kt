package com.harrypotter.app.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.harrypotter.app.R
import com.harrypotter.app.domain.model.Character
import com.harrypotter.app.ui.composables.CachedImage
import com.harrypotter.app.ui.composables.TextItem
import com.harrypotter.app.utils.convertDate
import com.harrypotter.app.utils.replaceEmptyString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(character: Character, back:()-> Unit){
    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.background),
            title = {
                Text(
                    text = stringResource(R.string.character_details),
                    style =
                    MaterialTheme.typography.bodyMedium.copy(
                        color =  MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.W500,
                        textAlign = TextAlign.Center,
                    ),
                )
            },
            navigationIcon = {
                IconButton(onClick =
                back
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back", tint =  MaterialTheme.colorScheme.onBackground, )
                }
            },

            )
    }) { innerPadding ->


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(1f).padding(20.dp).padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            CachedImage(
                borderRadius = 10.dp,
                url = character.image ?: "",
            )
            Spacer(modifier = Modifier.height(30.dp))
            TextItem(character.name.replaceEmptyString())
            TextItem(character.actor.replaceEmptyString())
            TextItem(character.species.replaceEmptyString())
            TextItem(convertDate(character.dateOfBirth))
            TextItem(if(character.alive==true){
                stringResource(R.string.alive)
            }else{
                stringResource(R.string.dead)
            })
        }
    }
}
