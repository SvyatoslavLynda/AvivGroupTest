package com.svdroid.avivgrouptest.ui.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.svdroid.avivgrouptest.R

@Composable
fun LoadingState(modifier: Modifier = Modifier) {
    Text(text = stringResource(id = R.string.message_loading), modifier = modifier)
}