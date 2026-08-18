package com.sleepyhead.patterns.mvvm.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.sleepyhead.patterns.mvvm.compose.navigation.ComposeMvvmNavHost
import com.sleepyhead.patterns.mvvm.ui.theme.ShowcaseTheme

class ComposeMvvmActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShowcaseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    ComposeMvvmNavHost(onClose = ::finish)
                }
            }
        }
    }
}
