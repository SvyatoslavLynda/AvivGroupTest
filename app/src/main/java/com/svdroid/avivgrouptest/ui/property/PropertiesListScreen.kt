package com.svdroid.avivgrouptest.ui.property

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.svdroid.avivgrouptest.R
import com.svdroid.avivgrouptest.ui.main.Destinations

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PropertiesListScreen(rootNavController: NavController, navController: NavController, title: String) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = title) }, actions = {
                IconButton(
                    onClick = { rootNavController.navigate(Destinations.FILTER_ROUTE) },
                    content = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_filter_list_24),
                            contentDescription = ""
                        )
                    },
                )
            })
        },
        content = { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                Button(onClick = { navController.navigate(Destinations.PROPERTY_DETAIL_ROUTE) }) {
                    Text(text = "Open property")
                }
            }
        }
    )
}