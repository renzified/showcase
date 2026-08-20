package com.sleepyhead.fastmath

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.sleepyhead.fastmath.home.HomeScreen
import com.sleepyhead.showcase.uikit.themes.UiKitTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UiKitTheme {
                HomeScreen()
            }
        }
    }
}