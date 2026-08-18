package com.sleepyhead.patterns.mvi.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.sleepyhead.patterns.mvi.compose.navigation.ComposeMviNavHost
import com.sleepyhead.patterns.mvi.ui.theme.ShowcaseTheme

class ComposeMviActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShowcaseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    ComposeMviNavHost(onClose = ::finish)
                }
            }
        }
    }
}
