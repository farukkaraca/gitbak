package com.farukkaraca.gitbak.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.farukkaraca.gitbak.presentation.theme.GitBakTheme
import com.farukkaraca.gitbak.presentation.users.UserSearchScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class]

        enableEdgeToEdge()
        setContent {
            val state = mainViewModel.mainState.collectAsStateWithLifecycle()

            GitBakTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    UserSearchScreen(
                        isLoading =  state.value.isLoading,
                        users = state.value.userSearchResponse?.items,
                        onSearch = {
                            mainViewModel.searchUsers(it)
                        },
                        onClickUser = {

                        },
                        resetSearch = {
                            mainViewModel.resetSearch()
                        }
                    )
                }
            }
        }
    }
}