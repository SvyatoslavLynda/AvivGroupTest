package com.svdroid.avivgrouptest.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.svdroid.avivgrouptest.data.ui.UIFilters
import com.svdroid.avivgrouptest.ui.filter.FilterScreen
import com.svdroid.avivgrouptest.ui.property.MainPropertyNavGraph

object Destinations {
    const val MAIN_PROPERTY_ROUTE = "main_property"
    const val PROPERTY_LIST_ROUTE = "property_list"
    const val FAVORITE_PROPERTY_LIST_ROUTE = "favorite_property_list"
    const val PROPERTY_DETAIL_ROUTE = "property_details?property_id={property_id}"
    const val FILTER_ROUTE = "filter"

    fun propertyDetails(propertyId: Long) = "property_details?property_id=$propertyId"

    object Arguments {
        const val PROPERTY_ID = "property_id"
        const val FILTERS = "filters"
    }
}

@Composable
fun MainNavGraph(navController: NavHostController) {
    val actions = remember(navController) { MainActions(navController) }

    NavHost(navController = navController, startDestination = Destinations.MAIN_PROPERTY_ROUTE) {
        composable(route = Destinations.MAIN_PROPERTY_ROUTE, content = { MainPropertyNavGraph(navController) })
        composable(route = Destinations.FILTER_ROUTE, content = {
            val filters = navController.previousBackStackEntry?.savedStateHandle?.get<UIFilters?>(Destinations.Arguments.FILTERS)
            FilterScreen(actions = actions, filters = filters)
        })
    }
}

class MainActions(navController: NavHostController) {

    val applyFilters: (filters: UIFilters?) -> Unit = { filters ->
        navController.previousBackStackEntry?.savedStateHandle?.set(Destinations.Arguments.FILTERS, filters)
        navController.navigateUp()
    }

    val navigateUp: () -> Unit = {
        navController.navigateUp()
    }
}