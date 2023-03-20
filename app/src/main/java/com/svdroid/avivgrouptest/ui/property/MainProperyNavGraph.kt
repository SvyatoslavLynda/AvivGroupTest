package com.svdroid.avivgrouptest.ui.property

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.svdroid.avivgrouptest.data.ui.UIFilters
import com.svdroid.avivgrouptest.data.ui.UIPropertyModel
import com.svdroid.avivgrouptest.ui.main.Destinations
import com.svdroid.avivgrouptest.ui.property.details.PropertyScreen
import com.svdroid.avivgrouptest.ui.property.favorites.FavoritesPropertiesListScreen
import com.svdroid.avivgrouptest.ui.property.list.PropertiesListScreen

private sealed class BarItemData(val route: String, val icon: ImageVector) {
    object Properties : BarItemData(Destinations.PROPERTY_LIST_ROUTE, Icons.Filled.Home)
    object FavoriteProperties : BarItemData(Destinations.FAVORITE_PROPERTY_LIST_ROUTE, Icons.Filled.Favorite)
}

@Composable
fun MainPropertyNavGraph(rootNavController: NavController) {
    val navController = rememberNavController()
    val actions = remember(navController) { PropertiesActions(navController, rootNavController) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route?.substringBeforeLast("/")

    val items = listOf(
        BarItemData.Properties,
        BarItemData.FavoriteProperties,
    )

    Scaffold(
        bottomBar = {
            if (items.find { currentRoute == it.route } != null) NavigationBar {
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
        NavHost(navController, startDestination = BarItemData.Properties.route, Modifier.padding(innerPadding)) {
            composable(route = BarItemData.Properties.route) {
                val filters = rootNavController.currentBackStackEntry?.savedStateHandle?.get<UIFilters>(Destinations.Arguments.FILTERS)
                PropertiesListScreen(actions = actions, filters = filters)
            }
            composable(route = BarItemData.FavoriteProperties.route) { FavoritesPropertiesListScreen(actions = actions) }
            composable(
                route = Destinations.PROPERTY_DETAIL_ROUTE,
                arguments = listOf(navArgument(Destinations.Arguments.PROPERTY_ID) {
                    type = NavType.LongType
                    nullable = false
                }),
            ) { navBackStackEntry ->
                val propertyId =
                    navBackStackEntry.arguments?.getLong(Destinations.Arguments.PROPERTY_ID) ?: return@composable
                PropertyScreen(propertyId = propertyId, actions = actions)
            }
        }
    }
}

class PropertiesActions(navController: NavHostController, rootNavController: NavController) {

    val navigateToProperty: (audio: UIPropertyModel) -> Unit = { property ->
        navController.navigate(Destinations.propertyDetails(property.id))
    }

    val navigateToFilters: (filters: UIFilters?) -> Unit = { filters ->
        rootNavController.currentBackStackEntry?.savedStateHandle?.set(Destinations.Arguments.FILTERS, filters)
        rootNavController.navigate(Destinations.FILTER_ROUTE)
    }

    val navigateUp: () -> Unit = {
        navController.navigateUp()
    }
}