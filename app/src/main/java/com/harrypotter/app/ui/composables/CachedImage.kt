package com.harrypotter.app.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun CachedImage(
    url: String,
    borderRadius: Dp,
) {
    val painter =
        rememberAsyncImagePainter(
            ImageRequest
                .Builder(LocalContext.current)
                .data(url)
                .crossfade(true) // Optional: Adds a fade transition when loading
                .build(),
        )

    Box(
        modifier =
            Modifier.border(borderRadius, color = Color.Transparent).clip(
                RoundedCornerShape(borderRadius),
            ),
        contentAlignment = Alignment.Center
    ) {
       if(url.isEmpty()){
           Box( modifier = Modifier.height(100.dp).width(100.dp) .clip(RoundedCornerShape(borderRadius)))
       }
           else{ Image(
            contentScale = ContentScale.Fit,
            painter = painter,
            contentDescription = null,
            modifier = Modifier.heightIn(max = 200.dp).clip(RoundedCornerShape(borderRadius)), // Adjust size as needed
        )}
        if (painter.state is AsyncImagePainter.State.Loading) {
            ShimmerEffect(200.dp, width = 0.3f)
        }
    }
}
