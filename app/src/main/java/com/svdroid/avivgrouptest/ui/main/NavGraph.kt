package com.svdroid.avivgrouptest.ui.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.svdroid.avivgrouptest.ui.filter.FilterScreen
import com.svdroid.avivgrouptest.ui.property.MainPropertyNavGraph

object Destinations {
    const val MAIN_PROPERTY_ROUTE = "main_property"
    const val PROPERTY_LIST_ROUTE = "property_list"
    const val FAVORITE_PROPERTY_LIST_ROUTE = "favorite_property_list"
    const val PROPERTY_DETAIL_ROUTE = "property_detail"
    const val FILTER_ROUTE = "filter"

    object Arguments {
        const val PROPERTY_ID = "property_id"
    }
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Destinations.MAIN_PROPERTY_ROUTE) {
        composable(route = Destinations.MAIN_PROPERTY_ROUTE, content = { MainPropertyNavGraph(navController) })
        composable(route = Destinations.FILTER_ROUTE, content = { FilterScreen() })
    }
}