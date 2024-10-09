package com.harrypotter.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.harrypotter.app.ui.navigation.Navigation
import com.harrypotter.app.ui.theme.HarryPorterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HarryPorterTheme {
                Navigation()
            }
        }
    }
}


