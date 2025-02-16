package com.farukkaraca.gitbak.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.farukkaraca.gitbak.presentation.screens.profile.ProfileScreen
import com.farukkaraca.gitbak.presentation.state.MainState

@Composable
fun BottomNavigation(
    state: MainState,
    onLoginClick: () -> Unit,
    onClickLogout: () -> Unit,
    navController: NavHostController
) {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.background,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = {
                        Icon(
                            imageVector = if (selectedTab == 0)
                                Icons.Rounded.Search
                            else
                                Icons.Outlined.Search,
                            contentDescription = "Home"
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                        selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                    label = { Text("Search") }
                )

                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = {
                        Icon(
                            imageVector = if (selectedTab == 1)
                                Icons.Rounded.Person
                            else
                                Icons.Outlined.Person,
                            contentDescription = "Profile"
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                        selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                    label = { Text("Profile") }
                )
            }
        }
    ) { padding ->
        Surface(modifier = Modifier.padding(padding)) {
            when (selectedTab) {

                0 -> AppNavigation(
                    navController = navController,
                    state = state,
                    onLoginClick = onLoginClick
                )

                1 -> ProfileScreen(
                    onClickLogout = onClickLogout,
                    onClickFollowing = {

                    },
                    onClickFollowers = {

                    },
                    onClickRepos = {
                        navController.navigate("$USER_REPOS_SCREEN/${it.login}")
                    },
                )
            }
        }
    }
}

@Preview
@Composable
private fun BottomNavigationPref() {
    BottomNavigation(
        state = MainState(),
        onLoginClick = {},
        onClickLogout = {},
        navController = NavHostController(LocalContext.current)
    )
}
