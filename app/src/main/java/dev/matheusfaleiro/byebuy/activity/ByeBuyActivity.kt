package dev.matheusfaleiro.byebuy.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import dev.matheusfaleiro.byebuy.ui.theme.ByeBuyTheme
import dev.matheusfaleiro.corenavigation.presentation.graph.ByeBuyNavigationGraph

class ByeBuyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            ByeBuyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
                    ByeBuyNavigationGraph(modifier = Modifier.padding(paddingValues = paddingValues))
                }
            }
        }
    }
}
