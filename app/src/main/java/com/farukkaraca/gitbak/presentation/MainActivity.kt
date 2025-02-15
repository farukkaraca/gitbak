package com.farukkaraca.gitbak.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.farukkaraca.gitbak.presentation.navigation.AppNavigation
import com.farukkaraca.gitbak.presentation.theme.GitBakTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class]

        enableEdgeToEdge()
        setContent {
            GitBakTheme {
                AppNavigation()
            }
        }
    }
}