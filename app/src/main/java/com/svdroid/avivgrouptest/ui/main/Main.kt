package com.svdroid.avivgrouptest.ui.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.svdroid.avivgrouptest.ui.theme.AvivGroupTestTheme

@Composable
fun Main() {
    val navController = rememberNavController()

    AvivGroupTestTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            MainNavGraph(navController = navController)
        }
    }
}