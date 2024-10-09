package com.harrypotter.app.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.harrypotter.app.domain.model.Character
import com.harrypotter.app.utils.getHouseColor
import com.harrypotter.app.utils.replaceEmptyString

@Composable
fun CharacterItem(
   character: Character,
   onClick :()-> Unit
) {
    Box(
        modifier =
        Modifier
            .padding(start = 20.dp, end = 20.dp)
            .clickable {
                onClick()
              },
    ) {
        Box(
            modifier =
            Modifier
                .fillMaxWidth(1f)
                .background(getHouseColor(character.house!!), RoundedCornerShape(12.dp))
                .padding(start = 5.dp).shadow(4.dp, shape = RoundedCornerShape(12.dp), spotColor =MaterialTheme.colorScheme.onBackground),
            contentAlignment = Alignment.CenterEnd,
        ) {
            Box(
                modifier =
                Modifier
                    .fillMaxWidth(1f)
                    .background( MaterialTheme.colorScheme.background, RoundedCornerShape(12.dp))
                    .padding(12.dp),
            ) {
                Column {
                        Text(
                            text = character.name.replaceEmptyString(),
                            style =
                            MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onBackground,
                                fontWeight = FontWeight.W500,
                                textAlign = TextAlign.Start,
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )



                    Spacer(modifier = Modifier.height(15.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(1f),
                    ) {
                        Text(
                            text = character.actor.replaceEmptyString(),
                            modifier = Modifier.fillMaxWidth(0.5f),
                            textAlign = TextAlign.Start,
                            style =
                            MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onBackground,
                                fontWeight = FontWeight.W500,
                                textAlign = TextAlign.Center,
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis

                        )
                        Text(
                            text = character.species.replaceEmptyString(),
                            style =
                            MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onBackground,
                                fontWeight = FontWeight.W500,
                                textAlign = TextAlign.Center,
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                    }

                }
            }
        }
    }
}
