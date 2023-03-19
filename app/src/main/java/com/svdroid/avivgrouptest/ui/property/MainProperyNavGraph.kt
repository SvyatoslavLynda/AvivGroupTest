package com.svdroid.avivgrouptest.ui.property

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.svdroid.avivgrouptest.R
import com.svdroid.avivgrouptest.ui.main.Destinations

sealed class Screen(val route: String, @StringRes val titleResourceId: Int, val icon: ImageVector) {
    object Properties : Screen(Destinations.PROPERTY_LIST_ROUTE, R.string.properties, Icons.Filled.Home)
    object FavoriteProperties : Screen(
        Destinations.FAVORITE_PROPERTY_LIST_ROUTE,
        R.string.favorite_properties,
        Icons.Filled.Favorite,
    )
    object Property : Screen(Destinations.PROPERTY_DETAIL_ROUTE, R.string.favorite_properties, Icons.Filled.Favorite)
}

@Composable
fun MainPropertyNavGraph(rootNavController: NavController) {
    val navController = rememberNavController()

    val items = listOf(
        Screen.Properties,
        Screen.FavoriteProperties,
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = null) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController, startDestination = Screen.Properties.route, Modifier.padding(innerPadding)) {
            composable(Screen.Properties.route) {
                PropertiesListScreen(
                    rootNavController,
                    navController,
                    stringResource(Screen.Properties.titleResourceId)
                )
            }
            composable(Screen.FavoriteProperties.route) {
                PropertiesListScreen(
                    rootNavController,
                    navController,
                    stringResource(Screen.FavoriteProperties.titleResourceId)
                )
            }
            composable(Screen.Property.route) { PropertyScreen(navController) }
        }
    }
}