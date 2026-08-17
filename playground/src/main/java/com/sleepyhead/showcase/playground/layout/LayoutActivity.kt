package com.sleepyhead.showcase.playground.layout

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.dp

class LayoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            /*Layout { measurables, constraints ->
                FirstBox()
                SecondBox()
            }*/
        }
    }

    @Composable
    private fun FirstBox() {
        Box(modifier = Modifier.size(24.dp).background(Color.Green))
    }

    @Composable
    private fun SecondBox() {
        Box(modifier = Modifier.size(24.dp).background(Color.Red))
    }
}