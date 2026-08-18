package com.sleepyhead.patterns.mvvm

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sleepyhead.patterns.mvvm.compose.ComposeMvvmActivity
import com.sleepyhead.patterns.mvvm.ui.theme.ShowcaseTheme
import com.sleepyhead.patterns.mvvm.views.ViewsMvvmActivity

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShowcaseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                            .padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = "Strict MVVM",
                            style = MaterialTheme.typography.headlineMedium,
                            textAlign = TextAlign.Center,
                        )
                        Text(
                            text = "Shared ViewModels + repositories.\nPick a UI toolkit sample.",
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        Button(
                            onClick = {
                                startActivity(Intent(this@MainActivity, ComposeMvvmActivity::class.java))
                            },
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text("Compose sample")
                        }
                        OutlinedButton(
                            onClick = {
                                startActivity(Intent(this@MainActivity, ViewsMvvmActivity::class.java))
                            },
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text("Views (XML) sample")
                        }
                    }
                }
            }
        }
    }
}
