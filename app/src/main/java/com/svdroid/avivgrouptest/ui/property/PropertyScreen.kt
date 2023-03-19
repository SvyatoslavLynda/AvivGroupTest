package com.svdroid.avivgrouptest.ui.property

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PropertyScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("PropertyScreen") }, navigationIcon = {
                IconButton(
                    onClick = { navController.navigateUp() },
                    content = {
                        Icon(
                            Icons.Filled.Close,
                            contentDescription = ""
                        )
                    },
                )
            })
        },
        content = { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {

            }
        }
    )
}